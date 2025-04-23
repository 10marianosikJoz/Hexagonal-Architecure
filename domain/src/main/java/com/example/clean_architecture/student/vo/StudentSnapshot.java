package com.example.clean_architecture.student.vo;

import com.example.clean_architecture.course.vo.CourseSnapshot;

import java.util.Objects;
import java.util.Set;

public class StudentSnapshot {

    private StudentId studentId;
    private Firstname firstName;
    private Lastname lastName;
    private Email email;
    private Status status;
    private Set<CourseSnapshot> courses;

    private StudentSnapshot(final StudentId studentId,
                            final Firstname firstName,
                            final Lastname lastName,
                            final Email email,
                            final Status status,
                            final Set<CourseSnapshot> courses) {

        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.status = status;
        this.courses = Set.copyOf(courses);
    }

    public static StudentSnapshotBuilder builder() {
        return new StudentSnapshotBuilder();
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

    public Status getStatus() {
        return status;
    }

    public Set<CourseSnapshot> getCourses() {
        return courses;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        var that = (StudentSnapshot) o;
        return Objects.equals(studentId, that.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(studentId);
    }

    public enum Status {
        ACTIVE, INACTIVE
    }

    public static class StudentSnapshotBuilder {

        private Firstname firstName;
        private Lastname lastName;
        private Email email;
        private StudentSnapshot.Status status;
        private StudentId studentId;
        private Set<CourseSnapshot> courses;

        private StudentSnapshotBuilder() {}

        public StudentSnapshotBuilder withFirstname(Firstname firstName) {
            this.firstName = firstName;
            return this;
        }

        public StudentSnapshotBuilder withLastname(Lastname lastName) {
            this.lastName = lastName;
            return this;
        }

        public StudentSnapshotBuilder withEmail(Email email) {
            this.email = email;
            return this;
        }

        public StudentSnapshotBuilder withStatus(StudentSnapshot.Status status) {
            this.status = status;
            return this;
        }

        public StudentSnapshotBuilder withStudentId(StudentId studentId) {
            this.studentId = studentId;
            return this;
        }

        public StudentSnapshotBuilder withCourses(Set<CourseSnapshot> courses) {
            this.courses = courses;
            return this;
        }

        public StudentSnapshot build() {
            return new StudentSnapshot(studentId,
                                       firstName,
                                       lastName,
                                       email,
                                       status,
                                       courses);
        }
    }
}