package com.example.clean_architecture.course.vo;

import com.example.clean_architecture.teacher.exception.BusinessTeacherException;

import java.io.Serializable;
import java.time.LocalDateTime;

public class EndDate implements Serializable {

    private LocalDateTime value;

    protected EndDate() {}

    public EndDate(LocalDateTime value) {
        if (value == null) {
            throw new BusinessTeacherException("EndDate is not provided");
        }

        this.value = value;
    }

    public LocalDateTime getValue() {
        return value;
    }
}
