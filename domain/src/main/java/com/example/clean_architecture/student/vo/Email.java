package com.example.clean_architecture.student.vo;

import com.example.clean_architecture.teacher.exception.BusinessTeacherException;

import java.io.Serializable;

public class Email implements Serializable {

    private String value;

    protected Email() {}

    public Email(String value) {
        if (value == null || value.isEmpty() || value.isBlank()) {
            throw new BusinessTeacherException("Email is not provided");
        }

        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
