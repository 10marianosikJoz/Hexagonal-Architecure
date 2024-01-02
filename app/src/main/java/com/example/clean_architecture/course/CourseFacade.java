package com.example.clean_architecture.course;

import com.example.clean_architecture.DomainEventPublisher;
import com.example.clean_architecture.course.dto.CommandCourseDto;
import com.example.clean_architecture.course.exception.BusinessCourseException;
import com.example.clean_architecture.course.vo.CourseCreator;
import com.example.clean_architecture.course.vo.CourseEvent;
import com.example.clean_architecture.course.vo.CourseSnapshot;
import com.example.clean_architecture.student.*;
import com.example.clean_architecture.student.dto.QueryStudentDto;
import com.example.clean_architecture.student.exception.BusinessStudentException;
import com.example.clean_architecture.student.vo.Email;
import com.example.clean_architecture.student.vo.StudentEvent;
import com.example.clean_architecture.student.vo.StudentSnapshot;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CourseFacade {

    private final CourseRepository courseRepository;
    private final StudentFacade studentFacade;
    private final CourseFactory courseFactory;
    private final StudentQueryRepository studentQueryRepository;
    private final DomainEventPublisher publisher;

    CourseFacade(final CourseRepository courseRepository,
                 final StudentFacade studentFacade,
                 final CourseFactory courseFactory,
                 final StudentQueryRepository studentQueryRepository,
                 final DomainEventPublisher publisher) {

        this.courseRepository = courseRepository;
        this.studentFacade = studentFacade;
        this.courseFactory = courseFactory;
        this.studentQueryRepository = studentQueryRepository;
        this.publisher = publisher;
    }

    public void handleEvent(StudentEvent event) {
        var courseSnapshots = event.getData().courseSnapshots();
        switch (event.getState()) {
            case DELETED -> {
                updateCourseDetails(courseSnapshots);
                saveCourseCollection(courseSnapshots);
            }
            case CREATED, UPDATED -> {}
        }
    }

    private void updateCourseDetails(Set<CourseSnapshot> courseSnapshots) {
        courseSnapshots.forEach(CourseSnapshot::decreaseParticipantsNumber);
    }

    void saveCourseCollection(Set<CourseSnapshot> courseSnapshots) {
        courseRepository.saveAll(courseSnapshots.stream()
                                                .map(Course::restoreFromSnapshot)
                                                .collect(Collectors.toSet()));
    }

    @Transactional
    public void deleteCourseById(Long courseId) {
        courseRepository.findById(courseId)
                        .ifPresent(course -> {
                            courseRepository.deleteById(courseId);
                            publisher.publish(new CourseEvent(course.getSnapshot().getCourseId().getValue(),
                                                              null,
                                                              CourseEvent.State.DELETED));
                });
    }

    CommandCourseDto addNewCourse(CommandCourseDto course) {
        var persisted = courseRepository.save(courseFactory.from(course));
        publisher.publish(new CourseEvent(persisted.getSnapshot().getCourseId().getValue(),
                                          null,
                                          CourseEvent.State.CREATED));

        return toCourseDto(courseRepository.save(persisted));
    }

    List<CommandCourseDto> createCourses(Collection<CourseCreator> courses) {
        return courseRepository.saveAll(courses.stream()
                                               .map(Course::createFrom)
                                               .collect(Collectors.toList())).stream()
                                                                             .map(this::toCourseDto)
                                                                             .collect(Collectors.toList());
    }

    void courseEnrollment(Long courseId, Long studentId) {
        var course = courseRepository.findById(courseId)
                                     .orElseThrow(() -> BusinessCourseException.notFound("Course with id: " + courseId + " not found"));

        course.validateCourseStatus();
        var student = studentQueryRepository.findDtoByStudentIdValue(studentId)
                                            .orElseThrow(() -> BusinessStudentException.notFound("Student with id: " + studentId + " not found"));

        validateStudentBeforeCourseEnrollment(student, course);
        var studentSnapshot = StudentSnapshot.builder()
                                             .withStudentId(student.getStudentId())
                                             .withFirstname(student.getFirstName())
                                             .withLastname(student.getLastName())
                                             .withEmail(student.getEmail())
                                             .withCourses(student.getCourses())
                                             .withStatus(student.getStatus())
                                             .build();
        course.addStudent(studentSnapshot);
        incrementParticipantsNumber(course);
        courseRepository.save(course);
    }

    List<QueryStudentDto> getCourseMembers(Long courseId) {
        var course = courseRepository.findById(courseId)
                                     .orElseThrow(() -> BusinessCourseException.notFound("Course with id: " + courseId + " not found"));

        var courseMembers = course.getStudents().stream()
                                                .map(StudentFacade::restoreFromSnapshot)
                                                .map(QueryStudentDto::getEmail)
                                                .map(Email::getValue)
                                                .collect(Collectors.toList());
        return studentFacade.getStudentByEmails(courseMembers);
    }

    private void incrementParticipantsNumber(Course course) {
        course.getSnapshot().incrementParticipantsNumber();
        publisher.publish(course.changeStatus());
    }

    private void validateStudentBeforeCourseEnrollment(QueryStudentDto student, Course course) {
        if (StudentSnapshot.Status.INACTIVE.equals(student.getStatus())) {
            throw BusinessStudentException.notActive("Student is inactive");
        }

        if (course.getSnapshot().getStudents().stream()
                                              .map(StudentFacade::restoreFromSnapshot)
                                              .anyMatch(it -> student.getEmail().getValue().equals(it.getEmail().getValue()))) {
            throw BusinessCourseException.enrollment("Student with id: " + student.getStudentId() + " already enrolled this course");
        }
    }

    public CommandCourseDto toCourseDto(Course course) {
        var snapshot = course.getSnapshot();
        return CommandCourseDto.builder()
                               .withCourseId(snapshot.getCourseId())
                               .withName(snapshot.getName())
                               .withDescription(snapshot.getDescription())
                               .withStartDate(snapshot.getStartDate())
                               .withEndDate(snapshot.getEndDate())
                               .withParticipantLimit(snapshot.getParticipantLimit())
                               .withParticipantNumber(snapshot.getParticipantNumber())
                               .withStatus(snapshot.getStatus())
                               .withTeacher(snapshot.getTeacherSourceId())
                               .build();
    }
}
