package com.example.clean_architecture.student;

import com.example.clean_architecture.course.Status;
import com.example.clean_architecture.course.vo.*;
import com.example.clean_architecture.student.vo.Email;
import com.example.clean_architecture.student.vo.Firstname;
import com.example.clean_architecture.student.vo.Lastname;
import com.example.clean_architecture.student.vo.StudentId;
import com.example.clean_architecture.student.vo.StudentSnapshot;
import com.example.clean_architecture.teacher.vo.TeacherId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class StudentFacadeTest {

    private final InMemoryStudentRepository inMemoryStudentRepository = new InMemoryStudentRepository();
    private final InMemoryStudentQueryRepository inMemoryStudentQueryRepository = new InMemoryStudentQueryRepository();
    private final StudentEventPublisherTest studentEventPublisherTest = new StudentEventPublisherTest();
    private final StudentFacade studentFacade = new StudentFacade(inMemoryStudentRepository, inMemoryStudentQueryRepository, new StudentFactory(), studentEventPublisherTest);

    @BeforeEach
    void setUp() {
        inMemoryStudentRepository.truncate();
        inMemoryStudentQueryRepository.truncate();
    }

    @Test
    void shouldObtainListOfStudentsWithGivenEmails() {
        // given
        var emails = prepareEmailsData();
        var students = prepareStudentsData();
        inMemoryStudentRepository.save(students.get(0));
        inMemoryStudentRepository.save(students.get(1));

        // when
        var studentsByEmails = studentFacade.getStudentByEmails(emails).size();

        // then
        assertThat(studentsByEmails).isEqualTo(students.size());
    }

    @Test
    void shouldSaveStudentToDatabase() {
        // given
        var student = prepareSingleStudentData();
        var commandStudentDto = studentFacade.toStudentDto(student);

        // when
        var persisted = studentFacade.addNewStudent(commandStudentDto);

        // then
        assertThat(inMemoryStudentRepository.findById(persisted.studentId().value())).isPresent();
        assertThat(studentEventPublisherTest.getEvents().size()).isEqualTo(1);
    }

    @Test
    void shouldUpdateGivenStudent() {
        // given
        var student = prepareSingleStudentData();
        var commandStudentDto = studentFacade.toStudentDto(student);
        var studentId = student.getSnapshot().getStudentId().value();
        inMemoryStudentRepository.save(student);

        // when
        var updated = studentFacade.updateStudent(studentId, commandStudentDto);

        // then
        assertThat(inMemoryStudentRepository.findById(updated.studentId().value()).get().getSnapshot().getFirstName().value()).isEqualTo("John");
    }

    @Test
    void shouldDeleteStudentWithGivenId() {
        // given
        var student = prepareSingleStudentData();
        inMemoryStudentRepository.save(student);

        // when
        studentFacade.deleteStudentById(1L);

        // then
        assertThat(inMemoryStudentRepository.findById(student.getSnapshot().getStudentId().value())).isEmpty();
    }

    @Test
    void shouldDecreaseCourseParticipantNumberWhenDeleteGivenStudent() {
        // given
        var student = prepareSingleStudentData();
        var courses = Collections.singletonList(new ArrayList<>(student.getCourses()).getFirst());

        // when
        studentFacade.deleteStudentById(1L);

        // then
        assertThat(courses.getFirst().getParticipantNumber().participantNumber()).isEqualTo(2L);
    }

    private List<String> prepareEmailsData() {
        return List.of("john@gmail.com","elizabeth@gmail.com");
    }

    private List<Student> prepareStudentsData() {
        return List.of(
                Student.builder()
                        .withStudentId(new StudentId(1L))
                        .withFirstname(new Firstname("John"))
                        .withLastname(new Lastname("Murphy"))
                        .withEmail(new Email("john@gmail.com"))
                        .withStatus(StudentSnapshot.Status.ACTIVE)
                        .withCourses(new HashSet<>())
                        .build(),
                Student.builder()
                        .withStudentId(new StudentId(2L))
                        .withFirstname(new Firstname("Elizabeth"))
                        .withLastname(new Lastname("Mackenzie"))
                        .withEmail(new Email("elizabeth@gmail.com"))
                        .withStatus(StudentSnapshot.Status.ACTIVE)
                        .withCourses(new HashSet<>())
                        .build());
    }

    private Student prepareSingleStudentData() {
        var student = Student.builder()
                             .withStudentId(new StudentId(1L))
                             .withFirstname(new Firstname("John"))
                             .withLastname(new Lastname("Murphy"))
                             .withEmail(new Email("john@gmail.com"))
                             .withStatus(StudentSnapshot.Status.ACTIVE)
                             .withCourses(new HashSet<>())
                             .build();

        var course = CourseSnapshot.builder()
                                   .withCourseId(new CourseId(1L))
                                   .withName(new Name("Java course"))
                                   .withDescription(new Description("Course for beginners"))
                                   .withStartDate(new StartDate(LocalDateTime.of(2022, 11, 11, 20, 0, 0)))
                                   .withEndDate(new EndDate(LocalDateTime.of(2023, 1, 11, 20, 0, 0)))
                                   .withParticipantLimit(new ParticipantLimit(2L))
                                   .withParticipantNumber(new ParticipantNumber(2L))
                                   .withStatus(Status.ACTIVE)
                                   .withTeacherId(new TeacherId(1L))
                                   .build();

        student.getCourses().add(course);
        return student;
    }
}