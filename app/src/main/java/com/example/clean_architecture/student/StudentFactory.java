package com.example.clean_architecture.student;

import com.example.clean_architecture.student.dto.CommandStudentDto;
import com.example.clean_architecture.student.vo.StudentSnapshot;

class StudentFactory {

    Student from(CommandStudentDto source) {
        return Student.restoreFromSnapshot(StudentSnapshot.builder()
                                                          .withStudentId(source.studentId())
                                                          .withFirstname(source.firstName())
                                                          .withLastname(source.lastName())
                                                          .withEmail(source.email())
                                                          .withCourses(source.courses())
                                                          .withStatus(source.status())
                                                          .withCourses(source.courses())
                                                          .build());
    }
}