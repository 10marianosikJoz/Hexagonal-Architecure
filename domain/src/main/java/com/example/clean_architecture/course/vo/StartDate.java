package com.example.clean_architecture.course.vo;

import com.example.clean_architecture.teacher.exception.BusinessTeacherException;

import java.io.Serializable;
import java.time.LocalDateTime;

public class StartDate implements Serializable {

    private LocalDateTime value;

    protected StartDate() {}

    public StartDate(LocalDateTime value) {
        if (value == null) {
            throw new BusinessTeacherException("StartDate is not provided");
        }

        this.value = value;
    }

    public LocalDateTime getValue() {
        return value;
    }
}
