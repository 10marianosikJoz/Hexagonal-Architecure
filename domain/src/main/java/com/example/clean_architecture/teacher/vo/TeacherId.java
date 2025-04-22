package com.example.clean_architecture.teacher.vo;

import com.example.clean_architecture.teacher.exception.BusinessTeacherException;

public record TeacherId(Long teacherId) {

    public TeacherId {
        if (teacherId == null) {
            throw new BusinessTeacherException("TeacherId is not provided");
        }
    }
}