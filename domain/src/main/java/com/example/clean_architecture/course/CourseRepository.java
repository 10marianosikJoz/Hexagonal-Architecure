package com.example.clean_architecture.course;

import java.util.List;
import java.util.Optional;

interface CourseRepository {

    Optional<Course> findById(Long courseId);

    List<Course> saveAll(Iterable<Course> entities);

    void deleteById(Long id);

    Course save(Course course);
}