package com.example.clean_architecture.student;

import com.example.clean_architecture.course.vo.CourseSnapshot;
import com.example.clean_architecture.student.vo.Email;
import com.example.clean_architecture.student.vo.Firstname;
import com.example.clean_architecture.student.vo.Lastname;
import com.example.clean_architecture.student.event.StudentEvent;
import com.example.clean_architecture.student.vo.StudentId;
import com.example.clean_architecture.student.vo.StudentSnapshot;

import java.util.Set;

class Student {

    static Student restoreFromSnapshot(StudentSnapshot snapshot) {

        return Student.builder()
                             .withStudentId(snapshot.getStudentId())
                             .withFirstname(snapshot.getFirstName())
                             .withLastname(snapshot.getLastName())
                             .withEmail(snapshot.getEmail())
                             .withCourses(snapshot.getCourses())
                             .build();
    }

    static StudentSnapshot restoreFromStudent(Student student) {
        return StudentSnapshot.builder()
                              .withStudentId(student.studentId)
                              .withFirstname(student.firstName)
                              .withLastname(student.lastName)
                              .withEmail(student.email)
                              .withCourses(student.courses)
                              .withStatus(student.status)
                              .build();
    }

    static StudentBuilder builder() {
        return new StudentBuilder();
    }

    private Firstname firstName;
    private Lastname lastName;
    private Email email;
    private StudentSnapshot.Status status;
    private final StudentId studentId;
    private final Set<CourseSnapshot> courses;

    Student(final StudentId studentId,
            final Firstname firstName,
            final Lastname lastName,
            final Email email,
            final StudentSnapshot.Status status,
            Set<CourseSnapshot> courses) {

        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.status = status;
        this.courses = courses;
    }

    StudentEvent updateDetails(final Firstname firstName,
                               final Lastname lastName,
                               final Email email,
                               final StudentSnapshot.Status status) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.status = status;

        return new StudentEvent(studentId.value(),
                                new StudentEvent.Data(firstName, lastName, email, courses),
                                StudentEvent.State.UPDATED);
    }

    Set<CourseSnapshot> getCourses() {
        return courses;
    }

    StudentSnapshot getSnapshot() {
        return StudentSnapshot.builder()
                              .withStudentId(studentId)
                              .withFirstname(firstName)
                              .withLastname(lastName)
                              .withEmail(email)
                              .withCourses(courses)
                              .withStatus(status)
                              .build();
    }

    static class StudentBuilder {

        private Firstname firstName;
        private Lastname lastName;
        private Email email;
        private StudentSnapshot.Status status;
        private StudentId studentId;
        private Set<CourseSnapshot> courses;

        private StudentBuilder() {}

        StudentBuilder withFirstname(Firstname firstName) {
            this.firstName = firstName;
            return this;
        }

        StudentBuilder withLastname(Lastname lastName) {
            this.lastName = lastName;
            return this;
        }

        StudentBuilder withEmail(Email email) {
            this.email = email;
            return this;
        }

        StudentBuilder withStatus(StudentSnapshot.Status status) {
            this.status = status;
            return this;
        }

        StudentBuilder withStudentId(StudentId studentId) {
            this.studentId = studentId;
            return this;
        }

        StudentBuilder withCourses(Set<CourseSnapshot> courses) {
            this.courses = courses;
            return this;
        }

        Student build() {
            return new Student(studentId,
                               firstName,
                               lastName,
                               email,
                               status,
                               courses);
        }
    }
}