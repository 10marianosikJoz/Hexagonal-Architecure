package com.example.clean_architecture.student.dto;

import com.example.clean_architecture.course.vo.CourseSnapshot;
import com.example.clean_architecture.student.vo.StudentSnapshot;
import com.example.clean_architecture.student.vo.Email;
import com.example.clean_architecture.student.vo.Firstname;
import com.example.clean_architecture.student.vo.Lastname;
import com.example.clean_architecture.student.vo.StudentId;

import java.util.Set;

public record CommandStudentDto(StudentId studentId,
                                Firstname firstName,
                                Lastname lastName,
                                Email email,
                                Set<CourseSnapshot> courses,
                                StudentSnapshot.Status status) {

    static public CommandStudentDtoBuilder builder() {
        return new CommandStudentDtoBuilder();
    }

    public CommandStudentDto(StudentId studentId,
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

    public static class CommandStudentDtoBuilder {

        private StudentId studentId;
        private Firstname firstName;
        private Lastname lastName;
        private Email email;
        private StudentSnapshot.Status status;
        private Set<CourseSnapshot> courses;

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

        public CommandStudentDtoBuilder withCourses(Set<CourseSnapshot> courses) {
            this.courses = Set.copyOf(courses);
            return this;
        }

        public CommandStudentDto build() {
            return new CommandStudentDto(studentId,
                                         firstName,
                                         lastName,
                                         email,
                                         courses,
                                         status);
        }
    }
}