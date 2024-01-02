package com.example.clean_architecture.course;

import com.example.clean_architecture.course.dto.QueryCourseDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface CourseQueryRepository {

    long count();

    <T> Set<T> findBy(Class<T> type);

    List<QueryCourseDto> findAll();

    Optional<QueryCourseDto> findDtoByCourseIdValue(Long id);
}
