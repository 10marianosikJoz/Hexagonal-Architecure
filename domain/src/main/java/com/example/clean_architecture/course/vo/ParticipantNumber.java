package com.example.clean_architecture.course.vo;

import com.example.clean_architecture.teacher.exception.BusinessTeacherException;

import java.io.Serializable;
import java.util.Objects;

public class ParticipantNumber implements Serializable {

    private Long value;

    protected ParticipantNumber() {}

    public ParticipantNumber(Long value) {
        if (value == null) {
            throw new BusinessTeacherException("ParticipantNumber is not provided");
        }

        this.value = value;
    }

    public Long getValue() {
        return value;
    }

     void incrementValue() {
        value++;
    }

     void decrementValue() {
        value--;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final ParticipantNumber that = (ParticipantNumber) o;

        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
