package com.example.clean_architecture.course;

import com.example.clean_architecture.course.dto.QueryCourseDto;

import java.util.Optional;
import java.util.Set;

interface CourseQueryRepository {

    <T> Set<T> findBy(Class<T> type);

    Optional<QueryCourseDto> findDtoByCourseId(Long id);
}