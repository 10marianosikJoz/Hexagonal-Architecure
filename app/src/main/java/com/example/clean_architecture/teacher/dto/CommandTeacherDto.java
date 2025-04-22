package com.example.clean_architecture.teacher.dto;

import com.example.clean_architecture.teacher.vo.Degree;
import com.example.clean_architecture.teacher.vo.Firstname;
import com.example.clean_architecture.teacher.vo.Lastname;
import com.example.clean_architecture.teacher.vo.TeacherId;

public record CommandTeacherDto(Firstname firstName,
                                Lastname lastName,
                                Degree degree,
                                TeacherId teacherId) {

    public static CommandTeacherBuilder builder() {
        return new CommandTeacherBuilder();
    }

    public static class CommandTeacherBuilder {

        private Firstname firstName;
        private Lastname lastName;
        private Degree degree;
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

        public CommandTeacherDto build() {
            return new CommandTeacherDto(firstName,
                                         lastName,
                                         degree,
                                         teacherId);
        }
    }
}