package com.example.clean_architecture.teacher;

import com.example.clean_architecture.teacher.dto.CommandTeacherDto;
import com.example.clean_architecture.teacher.vo.TeacherSnapshot;

class TeacherFactory {

    Teacher from(final CommandTeacherDto source) {
        return Teacher.restoreFromSnapshot(TeacherSnapshot.builder()
                                                          .withFirstname(source.getFirstName())
                                                          .withLastname(source.getLastName())
                                                          .withDegree(source.getDegree())
                                                          .withTeacherSourceId(source.getTeacherSourceId())
                                                          .withTeacherId(source.getTeacherId())
                                                          .build());
    }
}
