package com.example.clean_architecture.teacher.vo;

public class TeacherSnapshot {
    private TeacherId teacherId;
    private Firstname firstName;
    private Lastname lastName;
    private Degree degree;

    private TeacherSnapshot(final TeacherId teacherId,
                            final Firstname firstName,
                            final Lastname lastName,
                            final Degree degree) {

        this.teacherId = teacherId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.degree = degree;
    }

    public static TeacherSnapshotBuilder builder() {
        return new TeacherSnapshotBuilder();
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

    public static class TeacherSnapshotBuilder {

        private TeacherId teacherId;
        private Firstname firstName;
        private Lastname lastName;
        private Degree degree;

        private TeacherSnapshotBuilder() {}

        public TeacherSnapshotBuilder withTeacherId(TeacherId teacherId) {
            this.teacherId = teacherId;
            return this;
        }

        public TeacherSnapshotBuilder withFirstname(Firstname firstName) {
            this.firstName = firstName;
            return this;
        }

        public TeacherSnapshotBuilder withLastname(Lastname lastName) {
            this.lastName = lastName;
            return this;
        }

        public TeacherSnapshotBuilder withDegree(Degree degree) {
            this.degree = degree;
            return this;
        }

        public TeacherSnapshot build() {
            return new TeacherSnapshot(teacherId,
                                       firstName,
                                       lastName,
                                       degree);
        }
    }
}