package com.example.clean_architecture.student;

import java.util.List;
import java.util.Optional;

interface StudentRepository {

    Optional<Student> findById(Long studentId);

    void deleteById(Long id);

    Student save(Student student);

    List<Student> findAll();

    List<Student> findAllByEmailIn(List<String> emails);
}
