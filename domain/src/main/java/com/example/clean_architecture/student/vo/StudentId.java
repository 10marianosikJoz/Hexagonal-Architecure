package com.example.clean_architecture.student.vo;

import com.example.clean_architecture.teacher.exception.BusinessTeacherException;

import java.io.Serializable;

public class StudentId implements Serializable {

    private Long value;

    private StudentId() {}

    public StudentId(Long value) {
        if (value == null) {
            throw new BusinessTeacherException("StudentId is not provided");
        }

        this.value = value;
    }

    public Long getValue() {
        return value;
    }
}
