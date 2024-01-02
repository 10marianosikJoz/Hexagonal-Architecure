package com.example.clean_architecture.teacher.vo;

import com.example.clean_architecture.teacher.exception.BusinessTeacherException;

import java.io.Serializable;

public class Degree implements Serializable {

    private String value;

    protected Degree() {}

    public Degree(String value) {
        if (value == null || value.isEmpty() || value.isBlank()) {
            throw new BusinessTeacherException("Degree is not provided");
        }

        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
