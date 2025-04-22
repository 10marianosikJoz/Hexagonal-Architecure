package com.example.clean_architecture.student.vo;

import com.example.clean_architecture.teacher.exception.BusinessTeacherException;

public record Lastname(String value) {

    public Lastname {
        if (value == null || value.isBlank()) {
            throw new BusinessTeacherException("LastName is not provided.");
        }
    }
}