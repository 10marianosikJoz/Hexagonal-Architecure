package com.example.clean_architecture.teacher;

import com.example.clean_architecture.teacher.dto.CommandTeacherDto;
import com.example.clean_architecture.teacher.vo.TeacherSnapshot;

class TeacherFactory {

    Teacher from(final CommandTeacherDto source) {
        return Teacher.restoreFromSnapshot(TeacherSnapshot.builder()
                                                          .withFirstname(source.firstName())
                                                          .withLastname(source.lastName())
                                                          .withDegree(source.degree())
                                                          .withTeacherId(source.teacherId())
                                                          .build());
    }
}