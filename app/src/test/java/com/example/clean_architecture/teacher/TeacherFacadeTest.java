package com.example.clean_architecture.teacher;

import com.example.clean_architecture.teacher.dto.CommandTeacherDto;
import com.example.clean_architecture.teacher.vo.Degree;
import com.example.clean_architecture.teacher.vo.Firstname;
import com.example.clean_architecture.teacher.vo.Lastname;
import com.example.clean_architecture.teacher.vo.TeacherId;
import com.example.clean_architecture.teacher.vo.TeacherSourceId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TeacherFacadeTest {

    @Mock
    TeacherRepository teacherRepository;

    @InjectMocks
    TeacherFacade teacherFacade;

    @Test
    void shouldDeleteGivenTeacherFromDatabase() {
        var teacher = prepareTeacherData();
        given(teacherRepository.findByTeacherIdValue(1L)).willReturn(Optional.of(teacher));
        doNothing().when(teacherRepository).deleteByTeacherIdValue(1L);

        teacherFacade.deleteTeacherById(1L);
        
        verify(teacherRepository, times(1)).deleteByTeacherIdValue(1L);
    }

    @Test
    void shouldSaveNewTeacherToDatabase() {
        var teacher = prepareTeacherData();
        var commandTeacherDto = teacherFacade.toTeacherDto(teacher);
        given(teacherRepository.findByTeacherIdValue(any())).willReturn(Optional.of(teacher));
        given(teacherRepository.save(teacher)).willReturn(teacher);

        var persisted = teacherFacade.addNewTeacher(commandTeacherDto);

        assertThat(persisted.getFirstName().getValue()).isEqualTo("John");
    }

    @Test
    void shouldUpdateGivenTeacher() {
        var teacher = prepareTeacherData();
        var commandTeacherDto = prepareTeacherDtoData();
        var teacherId = teacher.getSnapshot().getTeacherId().getValue();
        given(teacherRepository.findByTeacherIdValue(teacherId)).willReturn(Optional.of(teacher));
        given(teacherRepository.save(teacher)).willReturn(teacher);

        var updated = teacherFacade.updateTeacher(teacherId, commandTeacherDto);

        assertThat(updated.getFirstName().getValue()).isEqualTo("John");
    }

    private Teacher prepareTeacherData() {
        return Teacher.builder()
                      .withFirstname(new Firstname("John"))
                      .withLastname(new Lastname("Murphy"))
                      .withDegree(new Degree("Master degree"))
                      .withTeacherSourceId(new TeacherSourceId("1"))
                      .withTeacherId(new TeacherId(1L))
                      .build();
    }

    private CommandTeacherDto prepareTeacherDtoData() {
        return CommandTeacherDto.builder()
                                .withFirstname(new Firstname("John"))
                                .withLastname(new Lastname("Murphy"))
                                .withDegree(new Degree("Master degree"))
                                .withTeacherSourceId(new TeacherSourceId("1"))
                                .build();
    }
}