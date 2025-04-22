package com.example.clean_architecture.student.vo;

import com.example.clean_architecture.teacher.exception.BusinessTeacherException;

public record StudentId(Long value) {

    public StudentId {
        if (value == null) {
            throw new BusinessTeacherException("StudentId is not provided.");
        }
    }
}