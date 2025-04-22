package com.example.clean_architecture.course;

import com.example.clean_architecture.DomainEventPublisher;
import com.example.clean_architecture.course.dto.CommandCourseDto;
import com.example.clean_architecture.course.exception.BusinessCourseException;
import com.example.clean_architecture.course.vo.CourseCreator;
import com.example.clean_architecture.course.event.CourseEvent;
import com.example.clean_architecture.course.vo.CourseSnapshot;
import com.example.clean_architecture.student.*;
import com.example.clean_architecture.student.dto.QueryStudentDto;
import com.example.clean_architecture.student.exception.BusinessStudentException;
import com.example.clean_architecture.student.vo.Email;
import com.example.clean_architecture.student.event.StudentEvent;
import com.example.clean_architecture.student.vo.StudentSnapshot;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CourseFacade {

    private final CourseRepository courseRepository;
    private final StudentFacade studentFacade;
    private final CourseFactory courseFactory;
    private final DomainEventPublisher publisher;

    CourseFacade(final CourseRepository courseRepository,
                 final StudentFacade studentFacade,
                 final CourseFactory courseFactory,
                 final DomainEventPublisher publisher) {

        this.courseRepository = courseRepository;
        this.studentFacade = studentFacade;
        this.courseFactory = courseFactory;
        this.publisher = publisher;
    }

    void handleEvent(StudentEvent event) {
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
        courseSnapshots.forEach(item -> item.getParticipantNumber().decreaseNumberOfParticipants());
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
                            publisher.publish(new CourseEvent(course.getSnapshot().getCourseId().courseId(),
                                                              new CourseEvent.Data(course.getSnapshot().getName(),
                                                                                   course.getSnapshot().getDescription(),
                                                                                   course.getSnapshot().getStartDate(),
                                                                                   course.getSnapshot().getEndDate()),
                                                              CourseEvent.State.DELETED));
                });
    }

    @Transactional
    CommandCourseDto addNewCourse(CommandCourseDto course) {
        var persisted = courseRepository.save(courseFactory.from(course));
        publisher.publish(new CourseEvent(persisted.getSnapshot().getCourseId().courseId(),
                                          new CourseEvent.Data(persisted.getSnapshot().getName(),
                                                               persisted.getSnapshot().getDescription(),
                                                               persisted.getSnapshot().getStartDate(),
                                                               persisted.getSnapshot().getEndDate()),
                                          CourseEvent.State.CREATED));

        return toCourseDto(courseRepository.save(persisted));
    }

    @Transactional
    List<CommandCourseDto> createCourses(Collection<CourseCreator> courses) {
        return courseRepository.saveAll(courses.stream()
                                               .map(Course::createFrom)
                                               .collect(Collectors.toList())).stream()
                                                                             .map(this::toCourseDto)
                                                                             .collect(Collectors.toList());
    }

    @Transactional
    void courseEnrollment(Long courseId, Long studentId) {
        var course = courseRepository.findById(courseId)
                                     .orElseThrow(() -> BusinessCourseException.notFound("Course with id: " + courseId + " not found"));

        course.validateCourseStatus();
        var student = studentFacade.findStudentDtoById(studentId);

        validateStudentBeforeCourseEnrollment(student, course);
        var studentSnapshot = StudentSnapshot.builder()
                                             .withStudentId(student.studentId())
                                             .withFirstname(student.firstName())
                                             .withLastname(student.lastName())
                                             .withEmail(student.email())
                                             .withCourses(student.courses())
                                             .withStatus(student.status())
                                             .build();
        course.addStudent(studentSnapshot);
        var participantNumber = course.getSnapshot().getParticipantNumber().increaseNumberOfParticipants();

        courseRepository.save(Course.restoreFromSnapshot(course.getSnapshot(), participantNumber));
    }

    @Transactional(readOnly = true)
    List<QueryStudentDto> getCourseMembers(Long courseId) {
        var course = courseRepository.findById(courseId)
                                     .orElseThrow(() -> BusinessCourseException.notFound("Course with id: " + courseId + " not found"));

        var courseMembers = course.students().stream()
                                                .map(studentFacade::restoreFromSnapshot)
                                                .map(QueryStudentDto::email)
                                                .map(Email::value)
                                                .collect(Collectors.toList());

        return studentFacade.getStudentByEmails(courseMembers);
    }

    private void validateStudentBeforeCourseEnrollment(QueryStudentDto student, Course course) {
        if (StudentSnapshot.Status.INACTIVE.equals(student.status())) {
            throw BusinessStudentException.notActive("Student is inactive");
        }

        if (course.getSnapshot().getStudents().stream()
                                              .map(studentFacade::restoreFromSnapshot)
                                              .anyMatch(it -> student.email().value().equals(it.email().value()))) {
            throw BusinessCourseException.enrollment("Student with id: " + student.studentId() + " already enrolled this course");
        }
    }

    CommandCourseDto toCourseDto(Course course) {
        return CommandCourseDto.builder()
                               .withCourseId(course.getSnapshot().getCourseId())
                               .withName(course.getSnapshot().getName())
                               .withDescription(course.getSnapshot().getDescription())
                               .withStartDate(course.getSnapshot().getStartDate())
                               .withEndDate(course.getSnapshot().getEndDate())
                               .withParticipantLimit(course.getSnapshot().getParticipantLimit())
                               .withParticipantNumber(course.getSnapshot().getParticipantNumber())
                               .withStatus(course.getSnapshot().getStatus())
                               .withTeacher(course.getSnapshot().getTeacherId())
                               .withStudents(course.getSnapshot().getStudents())
                               .build();
    }
}