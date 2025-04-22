package com.example.clean_architecture.course.vo;

import com.example.clean_architecture.teacher.exception.BusinessTeacherException;

import java.time.LocalDateTime;

public record EndDate(LocalDateTime endDate) {

    public EndDate {
        if (endDate == null) {
            throw new BusinessTeacherException("EndDate is not provided.");
        }
    }
}