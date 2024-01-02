package com.example.clean_architecture.student;

import com.example.clean_architecture.student.dto.CommandStudentDto;
import com.example.clean_architecture.student.vo.StudentSnapshot;

class StudentFactory {

    Student from(final CommandStudentDto source) {
        return Student.restoreFromSnapshot(StudentSnapshot.builder()
                                                          .withStudentId(source.getStudentId())
                                                          .withFirstname(source.getFirstName())
                                                          .withLastname(source.getLastName())
                                                          .withEmail(source.getEmail())
                                                          .withCourses(source.getCourses())
                                                          .withStatus(source.getStatus()).build());
    }
}
