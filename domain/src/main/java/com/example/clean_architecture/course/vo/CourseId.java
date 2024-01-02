package com.example.clean_architecture.course.vo;

import com.example.clean_architecture.teacher.exception.BusinessTeacherException;

import java.io.Serializable;

public class CourseId implements Serializable {

    private Long value;

    protected CourseId() {}

    public CourseId(Long value) {
        if (value == null) {
            throw new BusinessTeacherException("CourseId is not provided");
        }

        this.value = value;
    }

    public Long getValue() {
        return value;
    }
}
