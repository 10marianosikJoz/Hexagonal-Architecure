package com.example.clean_architecture.teacher.dto;

import com.example.clean_architecture.teacher.vo.Degree;
import com.example.clean_architecture.teacher.vo.Firstname;
import com.example.clean_architecture.teacher.vo.Lastname;
import com.example.clean_architecture.teacher.vo.TeacherId;
import com.example.clean_architecture.teacher.vo.TeacherSourceId;

import java.util.UUID;

public class CommandTeacherDto {

    public static CommandTeacherBuilder builder() {
        return new CommandTeacherBuilder();
    }

    private final Firstname firstName;
    private final Lastname lastName;
    private final Degree degree;
    private final TeacherSourceId teacherSourceId;
    private final TeacherId teacherId;

    public CommandTeacherDto(final Firstname firstName,
                             final Lastname lastName,
                             final Degree degree,
                             final TeacherSourceId teacherSourceId) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.degree = degree;
        this.teacherSourceId = teacherSourceId;
        this.teacherId = new TeacherId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
    }

    public TeacherId getTeacherId() {
        return teacherId;
    }

    public Firstname getFirstName() {
        return firstName;
    }

    public Lastname getLastName() {
        return lastName;
    }

    public Degree getDegree() {
        return degree;
    }

    public TeacherSourceId getTeacherSourceId() {
        return teacherSourceId;
    }

    public static class CommandTeacherBuilder {

        private Firstname firstName;
        private Lastname lastName;
        private Degree degree;
        private TeacherSourceId teacherSourceId;
        private TeacherId teacherId;

        public CommandTeacherBuilder withTeacherId(TeacherId teacherId) {
            this.teacherId = teacherId;
            return this;
        }

        public CommandTeacherBuilder withFirstname(Firstname firstName) {
            this.firstName = firstName;
            return this;
        }

        public CommandTeacherBuilder withLastname(Lastname lastName) {
            this.lastName = lastName;
            return this;
        }

        public CommandTeacherBuilder withDegree(Degree degree) {
            this.degree = degree;
            return this;
        }

        public CommandTeacherBuilder withTeacherSourceId(TeacherSourceId sourceId) {
            this.teacherSourceId = sourceId;
            return this;
        }

        public CommandTeacherDto build() {
            return new CommandTeacherDto(firstName,
                                         lastName,
                                         degree,
                                         teacherSourceId);
        }
    }
}
