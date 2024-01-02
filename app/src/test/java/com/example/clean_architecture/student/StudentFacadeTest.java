package com.example.clean_architecture.student;

import com.example.clean_architecture.DomainEventPublisher;
import com.example.clean_architecture.course.Status;
import com.example.clean_architecture.course.vo.*;
import com.example.clean_architecture.student.vo.Email;
import com.example.clean_architecture.student.vo.Firstname;
import com.example.clean_architecture.student.vo.Lastname;
import com.example.clean_architecture.student.vo.StudentId;
import com.example.clean_architecture.student.vo.StudentSnapshot;
import com.example.clean_architecture.teacher.vo.TeacherSourceId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentFacadeTest {

    @Mock
    StudentRepository studentRepository;

    @Mock
    StudentFactory studentFactory;

    @Mock
    DomainEventPublisher domainEventPublisher;

    @InjectMocks
    StudentFacade studentFacade;

    @Test
    void shouldObtainListOfStudentsWithGivenEmails() {
        var emails = prepareEmailsData();
        var students = prepareStudentsData();
        given(studentRepository.findAllByEmailIn(emails)).willReturn(students);

        var studentsByEmails = studentFacade.getStudentByEmails(emails).size();

        assertThat(studentsByEmails).isEqualTo(2);
    }

    @Test
    void shouldSaveStudentToDatabase() {
        var student = getStudentData();
        var commandStudentDto = StudentFacade.toStudentDto(student);
        doNothing().when(domainEventPublisher).publish(any());
        given(studentFactory.from(commandStudentDto)).willReturn(student);
        given(studentRepository.save(student)).willReturn(student);

        var persisted = studentFacade.addNewStudent(commandStudentDto);

        assertThat(persisted.getEmail().getValue()).isEqualTo("john@gmail.com");
    }

    @Test
    void shouldUpdateGivenStudent() {
        var student = getStudentData();
        var commandStudentDto = StudentFacade.toStudentDto(student);
        var studentId = student.getSnapshot().getStudentId().getValue();
        given(studentRepository.findById(studentId)).willReturn(Optional.of(student));
        given(studentRepository.save(student)).willReturn(student);

        var updated = studentFacade.updateStudent(studentId, commandStudentDto);

        assertThat(updated.getEmail().getValue()).isEqualTo("john@gmail.com");
    }

    @Test
    void shouldDeleteStudentWithGivenId() {
        var student = getStudentData();
        given(studentRepository.findById(1L)).willReturn(Optional.of(student));
        doNothing().when(domainEventPublisher).publish(any());
        doNothing().when(studentRepository).deleteById(1L);

        studentFacade.deleteStudentById(1L);

        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldDecreaseCourseParticipantNumberWhenDeleteGivenStudent() {
        var student = getStudentData();
        var courses = Collections.singletonList(mapToList(student.getCourses()).get(0));
        given(studentRepository.findById(1L)).willReturn(Optional.of(student));
        doNothing().when(domainEventPublisher).publish(any());
        doNothing().when(studentRepository).deleteById(1L);

        studentFacade.deleteStudentById(1L);

        assertThat(courses.get(0).getParticipantNumber().getValue()).isEqualTo(2L);
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
                        .build(),
                Student.builder()
                        .withStudentId(new StudentId(2L))
                        .withFirstname(new Firstname("Elizabeth"))
                        .withLastname(new Lastname("Mackenzie"))
                        .withEmail(new Email("elizabeth@gmail.com"))
                        .withStatus(StudentSnapshot.Status.ACTIVE)
                        .build());
    }

    private Student getStudentData() {
        var student = Student.builder()
                             .withStudentId(new StudentId(1L))
                             .withFirstname(new Firstname("John"))
                             .withLastname(new Lastname("Murphy"))
                             .withEmail(new Email("john@gmail.com"))
                             .withStatus(StudentSnapshot.Status.ACTIVE)
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
                                   .withTeacherSourceId(new TeacherSourceId("1"))
                                   .build();

        student.getCourses().add(course);
        return student;
    }

    private List<CourseSnapshot> mapToList(Set<CourseSnapshot> courses) {
        return new ArrayList<>(courses);
    }
}