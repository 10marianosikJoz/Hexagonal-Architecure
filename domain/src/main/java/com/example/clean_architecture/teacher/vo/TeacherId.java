package com.example.clean_architecture.teacher.vo;

import com.example.clean_architecture.teacher.exception.BusinessTeacherException;

import java.io.Serializable;

public class TeacherId implements Serializable {

    private Long value;

    protected TeacherId() {}

    public TeacherId(Long value) {
        if (value == null) {
            throw new BusinessTeacherException("TeacherId is not provided");
        }

        this.value = value;
    }

    public Long getValue() {
        return value;
    }
}
