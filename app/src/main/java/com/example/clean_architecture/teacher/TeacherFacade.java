package com.example.clean_architecture.teacher;

import com.example.clean_architecture.teacher.dto.CommandTeacherDto;
import com.example.clean_architecture.teacher.exception.BusinessTeacherException;
import org.springframework.transaction.annotation.Transactional;

class TeacherFacade {

    private final TeacherRepository teacherRepository;
    private final TeacherFactory teacherFactory;

    TeacherFacade(final TeacherRepository teacherRepository,
                  final TeacherFactory teacherFactory) {

        this.teacherRepository = teacherRepository;
        this.teacherFactory = teacherFactory;
    }

    @Transactional
    void deleteTeacherById(Long teacherId) {
        teacherRepository.findByTeacherId(teacherId)
                         .ifPresent(teacher -> teacherRepository.deleteByTeacherId(teacherId));
    }

    @Transactional
    CommandTeacherDto addNewTeacher(CommandTeacherDto teacher) {
        return toTeacherDto(teacherRepository.save(
                teacherRepository.findByTeacherId(teacher.teacherId().teacherId())
                        .map(teacherFromDB -> {
                            teacherFromDB.updateTeacherData(teacher.firstName(),
                                                            teacher.lastName(),
                                                            teacher.degree());
                            return teacherFromDB;
                        }).orElseGet(() -> teacherFactory.from(teacher))
        ));
    }

    @Transactional
     CommandTeacherDto updateTeacher(Long teacherId, CommandTeacherDto teacher) {
        return teacherRepository.findByTeacherId(teacherId)
                .map(teacherFromDB -> {
                   teacherFromDB.updateTeacherData(teacher.firstName(),
                                                   teacher.lastName(),
                                                   teacher.degree());

                    var persisted = teacherRepository.save(teacherFromDB);
                    return toTeacherDto(persisted);
                }).orElseThrow(() -> BusinessTeacherException.notFound("Teacher with id: " + teacherId + " not found"));
    }

     CommandTeacherDto toTeacherDto(Teacher teacher) {
        return CommandTeacherDto.builder()
                                .withTeacherId(teacher.getSnapshot().getTeacherId())
                                .withFirstname(teacher.getSnapshot().getFirstName())
                                .withLastname(teacher.getSnapshot().getLastName())
                                .withDegree(teacher.getSnapshot().getDegree())
                                .build();
    }
}