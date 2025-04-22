package com.example.clean_architecture.course.vo;

import com.example.clean_architecture.course.exception.BusinessCourseException;

public record CourseId(Long courseId) {

    public CourseId {
        if (courseId == null) {
            throw new BusinessCourseException("CourseId is not provided.");
        }
    }
}