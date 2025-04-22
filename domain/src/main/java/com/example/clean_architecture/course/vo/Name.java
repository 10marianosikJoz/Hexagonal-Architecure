package com.example.clean_architecture.course.vo;

import com.example.clean_architecture.teacher.exception.BusinessTeacherException;

public record Name(String name) {

    public Name {
        if (name == null) {
            throw new BusinessTeacherException("Name is not provided.");
        }
    }
}