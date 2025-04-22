package com.example.clean_architecture.student.vo;

import com.example.clean_architecture.teacher.exception.BusinessTeacherException;

public record Email(String value) {

    public Email {
        if (value == null || value.isBlank()) {
            throw new BusinessTeacherException("Email is not provided.");
        }
    }
}