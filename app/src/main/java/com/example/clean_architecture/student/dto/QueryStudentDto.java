package com.example.clean_architecture.student.dto;

import com.example.clean_architecture.course.vo.CourseSnapshot;
import com.example.clean_architecture.student.vo.StudentSnapshot;
import com.example.clean_architecture.student.vo.Email;
import com.example.clean_architecture.student.vo.Firstname;
import com.example.clean_architecture.student.vo.Lastname;
import com.example.clean_architecture.student.vo.StudentId;

import java.util.HashSet;
import java.util.Set;

public class QueryStudentDto {

    static public QueryStudentDtoBuilder builder() {
        return new QueryStudentDtoBuilder();
    }

    private final StudentId studentId;
    private final Firstname firstName;
    private final Lastname lastName;
    private final Email email;
    private final Set<CourseSnapshot> courses = new HashSet<>();
    private final StudentSnapshot.Status status;

    public QueryStudentDto(final StudentId studentId,
                           final Firstname firstName,
                           final Lastname lastName,
                           final Email email,
                           final StudentSnapshot.Status status) {

        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.status = status;
    }

    public static QueryStudentDto restoreFromCommandDto(CommandStudentDto commandStudentDto) {
        return new QueryStudentDto(commandStudentDto.getStudentId(),
                                   commandStudentDto.getFirstName(),
                                   commandStudentDto.getLastName(),
                                   commandStudentDto.getEmail(),
                                   commandStudentDto.getStatus());
    }

    public StudentId getStudentId() {
        return studentId;
    }

    public Firstname getFirstName() {
        return firstName;
    }

    public Lastname getLastName() {
        return lastName;
    }

    public Email getEmail() {
        return email;
    }

    public Set<CourseSnapshot> getCourses() {
        return courses;
    }

    public StudentSnapshot.Status getStatus() {
        return status;
    }

    public static class QueryStudentDtoBuilder {

        private StudentId id;
        private Firstname firstName;
        private Lastname lastName;
        private Email email;
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

        public QueryStudentDto build() {
            return new QueryStudentDto(id,
                                       firstName,
                                       lastName,
                                       email,
                                       status);
        }
    }
}
