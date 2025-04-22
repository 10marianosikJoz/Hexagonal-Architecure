package com.example.clean_architecture.teacher;

import java.util.Optional;

interface TeacherRepository {

    Optional<Teacher> findByTeacherId(Long teacherId);

    void deleteByTeacherId(Long teacherId);

    Teacher save(Teacher teacher);
}