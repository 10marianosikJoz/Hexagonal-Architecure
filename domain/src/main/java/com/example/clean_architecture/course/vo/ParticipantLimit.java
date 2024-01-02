package com.example.clean_architecture.course.vo;

import com.example.clean_architecture.teacher.exception.BusinessTeacherException;

import java.io.Serializable;

public class ParticipantLimit implements Serializable {

    private Long value;

    protected ParticipantLimit() {}

    public ParticipantLimit(Long value) {
        if (value == null) {
            throw new BusinessTeacherException("ParticipantLimit is not provided");
        }

        this.value = value;
    }

    public Long getValue() {
        return value;
    }
}
