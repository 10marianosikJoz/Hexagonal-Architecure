package com.example.clean_architecture.teacher.vo;

import com.example.clean_architecture.teacher.exception.BusinessTeacherException;

public record Degree(String value) {

    public Degree {
        if (value == null || value.isBlank()) {
            throw new BusinessTeacherException("Degree is not provided.");
        }
    }
}