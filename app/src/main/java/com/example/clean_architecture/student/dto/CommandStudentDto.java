package com.example.clean_architecture.student.dto;

import com.example.clean_architecture.course.vo.CourseSnapshot;
import com.example.clean_architecture.student.vo.StudentSnapshot;
import com.example.clean_architecture.student.vo.Email;
import com.example.clean_architecture.student.vo.Firstname;
import com.example.clean_architecture.student.vo.Lastname;
import com.example.clean_architecture.student.vo.StudentId;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CommandStudentDto {

    static public CommandStudentDtoBuilder builder() {
        return new CommandStudentDtoBuilder();
    }

    private final StudentId studentId;
    private final Firstname firstName;
    private final Lastname lastName;
    private final Email email;
    private final Set<CourseSnapshot> courses = new HashSet<>();
    private final StudentSnapshot.Status status;

    public CommandStudentDto(final Firstname firstName,
                             final Lastname lastName,
                             final Email email,
                             final StudentSnapshot.Status status) {

        this.studentId = new StudentId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.status = status;
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

    public static class CommandStudentDtoBuilder {

        private StudentId studentId;
        private Firstname firstName;
        private Lastname lastName;
        private Email email;
        private StudentSnapshot.Status status;

        public CommandStudentDtoBuilder withStudentId(StudentId studentId) {
            this.studentId = studentId;
            return this;
        }

        public CommandStudentDtoBuilder withFirstname(Firstname firstName) {
            this.firstName = firstName;
            return this;
        }

        public CommandStudentDtoBuilder withLastname(Lastname lastName) {
            this.lastName = lastName;
            return this;
        }

        public CommandStudentDtoBuilder withEmail(Email email) {
            this.email = email;
            return this;
        }

        public CommandStudentDtoBuilder withStatus(StudentSnapshot.Status status) {
            this.status = status;
            return this;
        }

        public CommandStudentDto build() {
            return new CommandStudentDto(firstName,
                                         lastName,
                                         email,
                                         status);
        }
    }
}
