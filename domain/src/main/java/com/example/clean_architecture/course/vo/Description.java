package com.example.clean_architecture.course.vo;

import com.example.clean_architecture.teacher.exception.BusinessTeacherException;

public record Description(String description) {

    public Description {
        if (description == null || description.isBlank()) {
            throw new BusinessTeacherException("Description is not provided.");
        }
    }
}