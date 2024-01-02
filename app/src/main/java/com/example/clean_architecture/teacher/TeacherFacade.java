package com.example.clean_architecture.teacher;

import com.example.clean_architecture.teacher.dto.CommandTeacherDto;
import com.example.clean_architecture.teacher.exception.BusinessTeacherException;

import javax.transaction.Transactional;

 class TeacherFacade {

    private final TeacherRepository teacherRepository;
    private final TeacherFactory teacherFactory;

    TeacherFacade(final TeacherRepository teacherRepository,
                  final TeacherFactory teacherFactory) {

        this.teacherRepository = teacherRepository;
        this.teacherFactory = teacherFactory;
    }

    @Transactional
    public void deleteTeacherById(Long teacherId) {
        teacherRepository.findByTeacherIdValue(teacherId)
                         .ifPresent(teacher -> teacherRepository.deleteByTeacherIdValue(teacherId));
    }

    CommandTeacherDto addNewTeacher(CommandTeacherDto teacher) {
        return toTeacherDto(teacherRepository.save(
                teacherRepository.findByTeacherIdValue(teacher.getTeacherId().getValue())
                        .map(teacherFromDB -> {
                            teacherFromDB.updateTeacherData(teacher.getFirstName(),
                                                            teacher.getLastName(),
                                                            teacher.getDegree());
                            return teacherFromDB;
                        }).orElseGet(() -> teacherFactory.from(teacher))
        ));
    }

     CommandTeacherDto updateTeacher(Long teacherId, CommandTeacherDto teacher) {
        return teacherRepository.findByTeacherIdValue(teacherId)
                .map(teacherFromDB -> {
                   teacherFromDB.updateTeacherData(teacher.getFirstName(),
                                                   teacher.getLastName(),
                                                   teacher.getDegree());

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
