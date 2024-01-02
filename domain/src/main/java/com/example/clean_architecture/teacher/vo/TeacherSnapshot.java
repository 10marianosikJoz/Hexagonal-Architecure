package com.example.clean_architecture.teacher.vo;

public class TeacherSnapshot {

    public static TeacherSnapshotBuilder builder() {
        return new TeacherSnapshotBuilder();
    }

    private Firstname firstName;
    private Lastname lastName;
    private Degree degree;
    private TeacherId teacherId;
    private TeacherSourceId teacherSourceId;

    protected TeacherSnapshot() {}

    public TeacherSnapshot(final Firstname firstName,
                           final Lastname lastName,
                           final Degree degree,
                           final TeacherSourceId teacherSourceId,
                           final TeacherId teacherId) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.degree = degree;
        this.teacherSourceId = teacherSourceId;
        this.teacherId = teacherId;
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

    public TeacherSourceId getTeacherSourceId() {
        return teacherSourceId;
    }

    public static class TeacherSnapshotBuilder {

        private Firstname firstName;
        private Lastname lastName;
        private Degree degree;
        private TeacherId teacherId;
        private TeacherSourceId teacherSourceId;

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

        public TeacherSnapshotBuilder withTeacherId(TeacherId teacherId) {
            this.teacherId = teacherId;
            return this;
        }

        public TeacherSnapshotBuilder withTeacherSourceId(TeacherSourceId teacherSourceId) {
            this.teacherSourceId = teacherSourceId;
            return this;
        }

        public TeacherSnapshot build() {
            return new TeacherSnapshot(firstName,
                                       lastName,
                                       degree,
                                       teacherSourceId,
                                       teacherId);
        }
    }
}
