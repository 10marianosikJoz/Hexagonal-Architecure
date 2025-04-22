package com.example.clean_architecture.teacher.dto;

import com.example.clean_architecture.teacher.vo.Degree;
import com.example.clean_architecture.teacher.vo.Firstname;
import com.example.clean_architecture.teacher.vo.Lastname;
import com.example.clean_architecture.teacher.vo.TeacherId;

public record QueryTeacherDto(Firstname firstName,
                              Lastname lastName,
                              Degree degree,
                              TeacherId teacherId) {

    public static QueryTeacherBuilder builder() {
        return new QueryTeacherBuilder();
    }

    public static QueryTeacherDto restoreFromCommandDto(CommandTeacherDto commandTeacherDto) {
        return new QueryTeacherDto(commandTeacherDto.firstName(),
                                   commandTeacherDto.lastName(),
                                   commandTeacherDto.degree(),
                                   commandTeacherDto.teacherId());
    }

    public static class QueryTeacherBuilder {

        private TeacherId teacherId;
        private Firstname firstName;
        private Lastname lastName;
        private Degree degree;

        public QueryTeacherBuilder withTeacherId(TeacherId teacherId) {
            this.teacherId = teacherId;
            return this;
        }

        public QueryTeacherBuilder withFirstname(Firstname firstName) {
            this.firstName = firstName;
            return this;
        }

        public QueryTeacherBuilder withLastname(Lastname lastName) {
            this.lastName = lastName;
            return this;
        }

        public QueryTeacherBuilder withDegree(Degree degree) {
            this.degree = degree;
            return this;
        }

        public QueryTeacherDto build() {
            return new QueryTeacherDto(firstName,
                                       lastName,
                                       degree,
                                       teacherId);
        }
    }
}