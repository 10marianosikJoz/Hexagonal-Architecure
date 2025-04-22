package com.example.clean_architecture.teacher;

import com.example.clean_architecture.teacher.vo.Degree;
import com.example.clean_architecture.teacher.vo.Firstname;
import com.example.clean_architecture.teacher.vo.Lastname;
import com.example.clean_architecture.teacher.vo.TeacherId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TeacherFacadeTest {

    private final InMemoryTeacherRepository inMemoryTeacherRepository = new InMemoryTeacherRepository();
    private final TeacherFacade teacherFacade = new TeacherFacade(inMemoryTeacherRepository, new TeacherFactory());

    @BeforeEach
    void setUp() {
        inMemoryTeacherRepository.truncate();
    }

    @Test
    void shouldDeleteGivenTeacherFromDatabase() {
        //given
        var initialTeacher = prepareTeacherData();
        inMemoryTeacherRepository.save(initialTeacher);

        //when
        teacherFacade.deleteTeacherById(1L);

        //then
        assertThat(inMemoryTeacherRepository.findByTeacherId(1L)).isEmpty();
    }

    @Test
    void shouldSaveNewTeacherToDatabase() {
        //given
        var intialTeacher = prepareTeacherData();
        var commandTeacherDto = teacherFacade.toTeacherDto(intialTeacher);

        //when
        var persisted = teacherFacade.addNewTeacher(commandTeacherDto);

        //then
        assertThat(inMemoryTeacherRepository.findByTeacherId(persisted.teacherId().teacherId())).isPresent();
    }

    @Test
    void shouldUpdateGivenTeacher() {
        //given
        var initialTeacher = prepareTeacherData();
        var commandTeacherDto = teacherFacade.toTeacherDto(initialTeacher);
        var teacherId = initialTeacher.getSnapshot().getTeacherId().teacherId();
        inMemoryTeacherRepository.save(initialTeacher);

        //when
        var updatedTeacher = teacherFacade.updateTeacher(teacherId, commandTeacherDto);

        //then
        assertThat(inMemoryTeacherRepository.findByTeacherId(updatedTeacher.teacherId().teacherId()).get().getSnapshot().getFirstName().value()).isEqualTo("John");
    }

    private Teacher prepareTeacherData() {
        return Teacher.builder()
                .withTeacherId(new TeacherId(1L))
                .withFirstname(new Firstname("John"))
                .withLastname(new Lastname("Murphy"))
                .withDegree(new Degree("Master degree"))
                .build();
    }
}