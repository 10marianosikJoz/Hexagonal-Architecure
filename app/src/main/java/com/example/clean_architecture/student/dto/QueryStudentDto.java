package com.example.clean_architecture.student.dto;

import com.example.clean_architecture.course.vo.CourseSnapshot;
import com.example.clean_architecture.student.vo.StudentSnapshot;
import com.example.clean_architecture.student.vo.Email;
import com.example.clean_architecture.student.vo.Firstname;
import com.example.clean_architecture.student.vo.Lastname;
import com.example.clean_architecture.student.vo.StudentId;

import java.util.Set;

public record QueryStudentDto(StudentId studentId,
                              Firstname firstName,
                              Lastname lastName,
                              Email email,
                              Set<CourseSnapshot> courses,
                              StudentSnapshot.Status status) {

    static public QueryStudentDtoBuilder builder() {
        return new QueryStudentDtoBuilder();
    }


    public static QueryStudentDto restoreFromCommandDto(CommandStudentDto commandStudentDto) {
        return new QueryStudentDto(commandStudentDto.studentId(),
                                   commandStudentDto.firstName(),
                                   commandStudentDto.lastName(),
                                   commandStudentDto.email(),
                                   commandStudentDto.courses(),
                                   commandStudentDto.status());
    }

    public QueryStudentDto(StudentId studentId,
                           Firstname firstName,
                           Lastname lastName,
                           Email email,
                           Set<CourseSnapshot> courses,
                           StudentSnapshot.Status status) {

        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.courses = Set.copyOf(courses);
        this.status = status;
    }

    public static class QueryStudentDtoBuilder {

        private StudentId id;
        private Firstname firstName;
        private Lastname lastName;
        private Email email;
        private Set<CourseSnapshot> courses;
        private StudentSnapshot.Status status;

        public QueryStudentDtoBuilder withStudentId(StudentId id) {
            this.id = id;
            return this;
        }

        public QueryStudentDtoBuilder withFirstname(Firstname firstName) {
            this.firstName = firstName;
            return this;
        }

        public QueryStudentDtoBuilder withLastname(Lastname lastName) {
            this.lastName = lastName;
            return this;
        }

        public QueryStudentDtoBuilder withEmail(Email email) {
            this.email = email;
            return this;
        }

        public QueryStudentDtoBuilder withStatus(StudentSnapshot.Status status) {
            this.status = status;
            return this;
        }

        public QueryStudentDtoBuilder withCourses(Set<CourseSnapshot> courses) {
            this.courses = Set.copyOf(courses);
            return this;
        }

        public QueryStudentDto build() {
            return new QueryStudentDto(id,
                                       firstName,
                                       lastName,
                                       email,
                                       courses,
                                       status);
        }
    }
}
