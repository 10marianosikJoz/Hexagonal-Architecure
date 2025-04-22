package com.example.clean_architecture.course;

import com.example.clean_architecture.course.exception.BusinessCourseException;
import com.example.clean_architecture.course.vo.*;
import com.example.clean_architecture.student.StudentFacade;
import com.example.clean_architecture.student.StudentModuleTestConfiguration;
import com.example.clean_architecture.student.dto.CommandStudentDto;
import com.example.clean_architecture.student.dto.QueryStudentDto;
import com.example.clean_architecture.student.exception.BusinessStudentException;
import com.example.clean_architecture.student.vo.Email;
import com.example.clean_architecture.student.vo.Firstname;
import com.example.clean_architecture.student.vo.Lastname;
import com.example.clean_architecture.student.vo.StudentId;
import com.example.clean_architecture.student.vo.StudentSnapshot;
import com.example.clean_architecture.teacher.vo.TeacherId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CourseFacadeTest {

    private final InMemoryCourseRepository inMemoryCourseRepository = new InMemoryCourseRepository();
    private final StudentFacade studentFacade = new StudentModuleTestConfiguration().studentFacade();
    private final CourseEventPublisherTest courseEventPublisherTest = new CourseEventPublisherTest();
    private final CourseFacade courseFacade = new CourseFacade(inMemoryCourseRepository, studentFacade, new CourseFactory(), courseEventPublisherTest);

    @BeforeEach
    void setUp() {
        inMemoryCourseRepository.truncate();

    }

    @Test
    void shouldReturnListOfCourseMembers() {
        //given
        var course = prepareCourseData();
        var student = prepareCommandStudentDtoData();
        studentFacade.addNewStudent(student);
        inMemoryCourseRepository.save(course);

        //when
        var courseMembers = courseFacade.getCourseMembers(1L);

        //then
        assertThat(courseMembers.getFirst().email().value()).isEqualTo("elizabeth@gmail.com");
    }

    @Test
    void shouldStudentEnrollGivenCourse() {
        //given
        var student = prepareCommandStudentDtoData();
        var course = prepareCourseData();
        inMemoryCourseRepository.save(course);

        //when
        courseFacade.courseEnrollment(course.getSnapshot().getCourseId().courseId(), student.studentId().value());

        //then
        assertThat(inMemoryCourseRepository.findById(1L).get().getSnapshot().getParticipantNumber().participantNumber()).isEqualTo(1L);
    }

    @Test
    void shouldNotAllowEnrollCourseForTheSameEmailAddress() {
        //given
        var course = prepareCourseData();
        var queryStudentDto = prepareStudentsDtoData().getFirst();

        //when
        //then
        assertThrows(BusinessCourseException.class, () ->
                courseFacade.courseEnrollment(course.getSnapshot().getCourseId().courseId(), queryStudentDto.studentId().value()));
    }

    @Test
    void shouldNotAllowEnrollCourseForWhenStudentIsInactive() {
        //given
        var course = prepareCourseData();
        var queryStudentDto = prepareStudentsDtoData().get(2);
        inMemoryCourseRepository.save(course);

        //when
        //then
        assertThrows(BusinessStudentException.class, () ->
                courseFacade.courseEnrollment(course.getSnapshot().getCourseId().courseId(), queryStudentDto.studentId().value()));
    }

    @Test
    void shouldSaveNewCourseToDatabase() {
        //given
        var course = prepareCourseData();
        var commandCourseDto = courseFacade.toCourseDto(course);

        //when
        var persisted = courseFacade.addNewCourse(commandCourseDto);

        //then
        assertThat(inMemoryCourseRepository.findById(persisted.courseId().courseId())).isPresent();
    }

    @Test
    void shouldDeleteGivenCourseFromDatabase() {
        //given
        var course = prepareCourseData();
        inMemoryCourseRepository.save(course);

        //when
        courseFacade.deleteCourseById(course.getSnapshot().getCourseId().courseId());

        //then
        assertThat(inMemoryCourseRepository.findById(course.getSnapshot().getCourseId().courseId())).isEmpty();
    }

    private Course prepareCourseData() {
        return Course.builder()
                     .withCourseId(new CourseId(1L))
                     .withName(new Name("Java course"))
                     .withDescription(new Description("Course for beginners"))
                     .withStartDate(new StartDate(LocalDateTime.of(2022, 11, 11, 20, 0, 0)))
                     .withEndDate(new EndDate(LocalDateTime.of(2023, 1, 11, 20, 0, 0)))
                     .withParticipantLimit(new ParticipantLimit(1L))
                     .withParticipantNumber(new ParticipantNumber(0L))
                     .withStatus(Status.ACTIVE)
                     .withTeacherId(new TeacherId(1L))
                     .withStudents(prepareStudentSnapshotData())
                     .build();
    }

    private Set<StudentSnapshot> prepareStudentSnapshotData() {
        var courses = new HashSet<CourseSnapshot>();
        courses.add(CourseSnapshot.builder().withCourseId(new CourseId(1L)).build());

        var students = new HashSet<StudentSnapshot>();

        students.add(StudentSnapshot.builder()
                                    .withStudentId(new StudentId(1L))
                                    .withFirstname(new Firstname("Elizabeth"))
                                    .withLastname(new Lastname("Faraday"))
                                    .withEmail(new Email("elizabeth@gmail.com"))
                                    .withCourses(courses)
                                    .withStatus(StudentSnapshot.Status.ACTIVE)
                                    .build());

        return students;
    }

    private List<QueryStudentDto> prepareStudentsDtoData() {
        var courses = new HashSet<CourseSnapshot>();
        courses.add(CourseSnapshot.builder().withCourseId(new CourseId(1L)).build());

        return List.of(QueryStudentDto.builder()
                                      .withStudentId(new StudentId(1L))
                                      .withFirstname(new Firstname("Elizabeth"))
                                      .withLastname(new Lastname("Faraday"))
                                      .withEmail(new Email("elizabeth@gmail.com"))
                                      .withStatus(StudentSnapshot.Status.ACTIVE)
                                      .withCourses(courses)
                                      .build(),
                      QueryStudentDto.builder()
                                      .withStudentId(new StudentId(2L))
                                      .withFirstname(new Firstname("John"))
                                      .withLastname(new Lastname("Murphy"))
                                      .withEmail(new Email("john@gmail.com"))
                                      .withStatus(StudentSnapshot.Status.ACTIVE)
                                      .withCourses(courses)
                                      .build(),
                      QueryStudentDto.builder()
                                      .withStudentId(new StudentId(3L))
                                      .withFirstname(new Firstname("Daniel"))
                                      .withLastname(new Lastname("Faraday"))
                                      .withEmail(new Email("faraday@gmail.com"))
                                      .withStatus(StudentSnapshot.Status.INACTIVE)
                                      .withCourses(courses)
                                      .build());
    }

    private CommandStudentDto prepareCommandStudentDtoData() {
        return CommandStudentDto.builder()
                                .withStudentId(new StudentId(1L))
                                .withEmail(new Email("elizabeth@gmail.com"))
                                .withCourses(Set.of(prepareCourseData().getSnapshot()))
                                .build();
    }
}