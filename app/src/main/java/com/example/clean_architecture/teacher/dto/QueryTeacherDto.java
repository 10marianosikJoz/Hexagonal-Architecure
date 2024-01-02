package com.example.clean_architecture.teacher.dto;

import com.example.clean_architecture.teacher.vo.Degree;
import com.example.clean_architecture.teacher.vo.Firstname;
import com.example.clean_architecture.teacher.vo.Lastname;
import com.example.clean_architecture.teacher.vo.TeacherId;
import com.example.clean_architecture.teacher.vo.TeacherSourceId;

public record QueryTeacherDto(Firstname firstName,
                              Lastname lastName,
                              Degree degree,
                              TeacherSourceId teacherSourceId,
                              TeacherId teacherId) {

    public static QueryTeacherBuilder builder() {
        return new QueryTeacherBuilder();
    }

    public static QueryTeacherDto restoreFromCommandDto(CommandTeacherDto commandTeacherDto) {
        return new QueryTeacherDto(commandTeacherDto.getFirstName(),
                                   commandTeacherDto.getLastName(),
                                   commandTeacherDto.getDegree(),
                                   commandTeacherDto.getTeacherSourceId(),
                                   commandTeacherDto.getTeacherId());
    }

    public static class QueryTeacherBuilder {

        private TeacherId teacherId;
        private Firstname firstName;
        private Lastname lastName;
        private Degree degree;
        private TeacherSourceId teacherSourceId;

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

        public QueryTeacherBuilder withTeacherSourceId(TeacherSourceId sourceId) {
            this.teacherSourceId = sourceId;
            return this;
        }

        public QueryTeacherDto build() {
            return new QueryTeacherDto(firstName,
                                       lastName,
                                       degree,
                                       teacherSourceId,
                                       teacherId);
        }
    }
}
