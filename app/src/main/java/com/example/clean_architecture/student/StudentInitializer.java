package com.example.clean_architecture.student;

import com.example.clean_architecture.student.vo.Email;
import com.example.clean_architecture.student.vo.Firstname;
import com.example.clean_architecture.student.vo.Lastname;
import com.example.clean_architecture.student.vo.StudentId;
import com.example.clean_architecture.student.vo.StudentSnapshot;

import java.util.Set;

class StudentInitializer {

    private final StudentRepository studentRepository;
    private final StudentQueryRepository studentQueryRepository;

    StudentInitializer(final StudentRepository studentRepository,
                       final StudentQueryRepository studentQueryRepository) {

        this.studentRepository = studentRepository;
        this.studentQueryRepository = studentQueryRepository;
    }

    void init() {
        if(studentQueryRepository.count() == 0) {
            studentRepository.save(Student.restoreFromSnapshot(StudentSnapshot.builder()
                                                                              .withStudentId(new StudentId(0L))
                                                                              .withFirstname(new Firstname("Elizabeth"))
                                                                              .withLastname(new Lastname("Faraday"))
                                                                              .withEmail(new Email("elizabeth@gmail.com"))
                                                                              .withCourses(Set.of())
                                                                              .withStatus(StudentSnapshot.Status.ACTIVE)
                                                                              .build()

            ));
        }
    }
}
