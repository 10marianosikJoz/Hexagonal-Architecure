package com.example.clean_architecture.course.vo;

import com.example.clean_architecture.teacher.exception.BusinessTeacherException;

import java.time.LocalDateTime;

public record StartDate(LocalDateTime startDate) {

    public StartDate {
        if (startDate == null) {
            throw new BusinessTeacherException("StartDate is not provided.");
        }
    }
}