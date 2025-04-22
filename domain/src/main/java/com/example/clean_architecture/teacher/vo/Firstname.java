package com.example.clean_architecture.teacher.vo;

import com.example.clean_architecture.teacher.exception.BusinessTeacherException;

public record Firstname(String value) {

    public Firstname {
        if (value == null || value.isBlank()) {
            throw new BusinessTeacherException("FirstName is not provided.");
        }
    }
}