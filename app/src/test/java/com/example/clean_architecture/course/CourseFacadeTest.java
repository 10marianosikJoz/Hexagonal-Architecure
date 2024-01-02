package com.example.clean_architecture.course;

import com.example.clean_architecture.DomainEventPublisher;
import com.example.clean_architecture.course.exception.BusinessCourseException;
import com.example.clean_architecture.course.vo.CourseId;
import com.example.clean_architecture.course.vo.Description;
import com.example.clean_architecture.course.vo.EndDate;
import com.example.clean_architecture.course.vo.Name;
import com.example.clean_architecture.course.vo.ParticipantLimit;
import com.example.clean_architecture.course.vo.ParticipantNumber;
import com.example.clean_architecture.course.vo.StartDate;
import com.example.clean_architecture.student.StudentFacade;
import com.example.clean_architecture.student.StudentQueryRepository;
import com.example.clean_architecture.student.dto.QueryStudentDto;
import com.example.clean_architecture.student.exception.BusinessStudentException;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CourseFacadeTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private StudentFacade studentFacade;

    @Mock
    private CourseFactory courseFactory;

    @Mock
    private StudentQueryRepository studentQueryRepository;

    @Mock
    private DomainEventPublisher domainEventPublisher;

    @InjectMocks
    private CourseFacade courseFacade;


    @Test
    void shouldReturnListOfCourseMembers() {
        var course = prepareCourseData();
        var queryStudentDto = prepareStudentsDtoData().get(0);
        given(courseRepository.findById(1L)).willReturn(Optional.of(course));
        given(studentFacade.getStudentByEmails(Collections.singletonList("elizabeth@gmail.com"))).willReturn(List.of(queryStudentDto));

        var courseMembers = courseFacade.getCourseMembers(1L);

        assertThat(courseMembers.get(0).getEmail().getValue()).isEqualTo("elizabeth@gmail.com");
    }

    @Test
    void shouldStudentEnrollGivenCourse() {
        var course = prepareCourseData();
        var queryStudentDto = prepareStudentsDtoData().get(1);
        given(courseRepository.findById(1L)).willReturn(Optional.of(course));
        given(studentQueryRepository.findDtoByStudentIdValue(2L)).willReturn(Optional.of(queryStudentDto));
        given(courseRepository.save(course)).willReturn(course);

        courseFacade.courseEnrollment(course.getSnapshot().getCourseId().getValue(), queryStudentDto.getStudentId().getValue());

        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void shouldIncreaseParticipantNumberWhenStudentEnrollCourse() {
        var course = prepareCourseData();
        var queryStudentDto = prepareStudentsDtoData().get(1);
        given(courseRepository.findById(1L)).willReturn(Optional.of(course));
        given(studentQueryRepository.findDtoByStudentIdValue(2L)).willReturn(Optional.of(queryStudentDto));
        given(courseRepository.save(course)).willReturn(course);

        courseFacade.courseEnrollment(course.getSnapshot().getCourseId().getValue(), queryStudentDto.getStudentId().getValue());

        assertThat(course.getSnapshot().getParticipantNumber().getValue()).isEqualTo(1L);
    }

    @Test
    void shouldNotAllowEnrollCourseForTheSameEmailAddress() {
        var course = prepareCourseData();
        var queryStudentDto = prepareStudentsDtoData().get(0);
        given(courseRepository.findById(1L)).willReturn(Optional.of(course));
        given(studentQueryRepository.findDtoByStudentIdValue(1L)).willReturn(Optional.of(queryStudentDto));

        assertThrows(BusinessCourseException.class, () ->
                courseFacade.courseEnrollment(
                        course.getSnapshot().getCourseId().getValue(), queryStudentDto.getStudentId().getValue()));
    }

    @Test
    void shouldNotAllowEnrollCourseForWhenStudentIsInactive() {
        var course = prepareCourseData();
        var queryStudentDto = prepareStudentsDtoData().get(2);
        given(courseRepository.findById(1L)).willReturn(Optional.of(course));
        given(studentQueryRepository.findDtoByStudentIdValue(3L)).willReturn(Optional.of(queryStudentDto));

        assertThrows(BusinessStudentException.class, () ->
                courseFacade.courseEnrollment(
                        course.getSnapshot().getCourseId().getValue(), queryStudentDto.getStudentId().getValue()));
    }

    @Test
    void shouldSaveNewCourseToDatabase() {
        var course = prepareCourseData();
        var commandCourseDto = courseFacade.toCourseDto(course);
        given(courseFactory.from(commandCourseDto)).willReturn(course);
        given(courseRepository.save(course)).willReturn(course);

        var persisted = courseFacade.addNewCourse(commandCourseDto);

        assertThat(persisted.getDescription().getValue()).isEqualTo("Course for beginners");
    }

    @Test
    void shouldDeleteGivenCourseFromDatabase() {
        var course = prepareCourseData();
        doNothing().when(domainEventPublisher).publish(any());
        doNothing().when(courseRepository).deleteById(1L);
        given(courseRepository.findById(1L)).willReturn(Optional.of(course));

        courseFacade.deleteCourseById(1L);

        verify(courseRepository, times(1)).deleteById(1L);
    }

    private Course prepareCourseData() {
        var course = Course.builder()
                           .withCourseId(new CourseId(1L))
                           .withName(new Name("Java course"))
                           .withDescription(new Description("Course for beginners"))
                           .withStartDate(new StartDate(LocalDateTime.of(2022, 11, 11, 20, 0, 0)))
                           .withEndDate(new EndDate(LocalDateTime.of(2023, 1, 11, 20, 0, 0)))
                           .withParticipantLimit(new ParticipantLimit(1L))
                           .withParticipantNumber(new ParticipantNumber(0L))
                           .withStatus(Status.ACTIVE)
                           .withTeacherSourceId(new TeacherSourceId("1"))
                           .build();

        course.addStudent(StudentSnapshot.builder()
                                         .withStudentId(new StudentId(0L))
                                         .withFirstname(new Firstname("Elizabeth"))
                                         .withLastname(new Lastname("Faraday"))
                                         .withEmail(new Email("elizabeth@gmail.com"))
                                         .withCourses(Set.of())
                                         .withStatus(StudentSnapshot.Status.ACTIVE)
                                         .build());
        return course;
    }

    private List<QueryStudentDto> prepareStudentsDtoData() {
        return List.of(QueryStudentDto.builder()
                                      .withStudentId(new StudentId(1L))
                                      .withFirstname(new Firstname("Elizabeth"))
                                      .withLastname(new Lastname("Faraday"))
                                      .withEmail(new Email("elizabeth@gmail.com"))
                                      .withStatus(StudentSnapshot.Status.ACTIVE)
                                      .build(),
                      QueryStudentDto.builder()
                                      .withStudentId(new StudentId(2L))
                                      .withFirstname(new Firstname("John"))
                                      .withLastname(new Lastname("Murphy"))
                                      .withEmail(new Email("john@gmail.com"))
                                      .withStatus(StudentSnapshot.Status.ACTIVE)
                                      .build(),
                      QueryStudentDto.builder()
                                      .withStudentId(new StudentId(3L))
                                      .withFirstname(new Firstname("Daniel"))
                                      .withLastname(new Lastname("Faraday"))
                                      .withEmail(new Email("faraday@gmail.com"))
                                      .withStatus(StudentSnapshot.Status.INACTIVE)
                                      .build());
    }
}