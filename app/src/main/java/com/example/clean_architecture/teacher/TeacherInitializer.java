package com.example.clean_architecture.teacher;

import com.example.clean_architecture.teacher.vo.Degree;
import com.example.clean_architecture.teacher.vo.Firstname;
import com.example.clean_architecture.teacher.vo.Lastname;
import com.example.clean_architecture.teacher.vo.TeacherId;
import com.example.clean_architecture.teacher.vo.TeacherSnapshot;

class TeacherInitializer {

    private final TeacherRepository teacherRepository;
    private final TeacherQueryRepository teacherQueryRepository;

    TeacherInitializer(final TeacherRepository teacherRepository,
                       final TeacherQueryRepository teacherQueryRepository) {

        this.teacherRepository = teacherRepository;
        this.teacherQueryRepository = teacherQueryRepository;
    }

    void init() {
        if(teacherQueryRepository.count() == 0) {
            teacherRepository.save(Teacher.restoreFromSnapshot(TeacherSnapshot.builder()
                                                                              .withFirstname(new Firstname("Clean"))
                                                                              .withLastname(new Lastname("Eastwood"))
                                                                              .withDegree(new Degree("Master degree"))
                                                                              .withTeacherId(new TeacherId(1L))
                                                                              .build()));
        }
    }
}
