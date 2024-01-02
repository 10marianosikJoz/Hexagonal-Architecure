package com.example.clean_architecture.course.vo;

import com.example.clean_architecture.teacher.exception.BusinessTeacherException;

import java.io.Serializable;

public class Name implements Serializable {

    private String value;

    protected Name() {}

    public Name(String value) {
        if (value == null) {
            throw new BusinessTeacherException("Name is not provided");
        }

        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
