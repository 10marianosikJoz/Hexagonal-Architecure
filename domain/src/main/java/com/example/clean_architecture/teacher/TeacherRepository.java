package com.example.clean_architecture.teacher;

import java.util.Optional;

interface TeacherRepository {

    Optional<Teacher> findByTeacherIdValue(Long teacherId);

    void deleteByTeacherIdValue(Long teacherId);

    Teacher save(Teacher teacher);
}
