package com.example.clean_architecture.course.vo;

import com.example.clean_architecture.teacher.exception.BusinessTeacherException;

import java.io.Serializable;

public class Description implements Serializable {

    private String value;

    protected Description() {}

    public Description(String value) {
        if (value == null || value.isEmpty() || value.isBlank()) {
            throw new BusinessTeacherException("Description is not provided");
        }

        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
