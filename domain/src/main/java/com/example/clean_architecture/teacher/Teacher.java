package com.example.clean_architecture.teacher;

import com.example.clean_architecture.teacher.vo.*;

class Teacher {

    static Teacher restoreFromSnapshot(TeacherSnapshot snapshot) {
        return Teacher.builder()
                      .withFirstname(snapshot.getFirstName())
                      .withLastname(snapshot.getLastName())
                      .withDegree(snapshot.getDegree())
                      .withTeacherId(snapshot.getTeacherId())
                      .build();
    }

    static TeacherBuilder builder() {
        return new TeacherBuilder();
    }

    private Firstname firstName;
    private Lastname lastName;
    private Degree degree;
    private final TeacherId teacherId;

    Teacher(final Firstname firstName,
            final Lastname lastName,
            final Degree degree,
            final TeacherId teacherId) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.degree = degree;
        this.teacherId = teacherId;
    }

    void updateTeacherData(Firstname firstName,
                           Lastname lastName,
                           Degree degree) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.degree = degree;
    }

    TeacherSnapshot getSnapshot() {
        return TeacherSnapshot.builder()
                              .withFirstname(firstName)
                              .withLastname(lastName)
                              .withDegree(degree)
                              .withTeacherId(teacherId)
                              .build();
    }

    static class TeacherBuilder {

        private Firstname firstName;
        private Lastname lastName;
        private Degree degree;
        private TeacherId teacherId;

        private TeacherBuilder() {}

        TeacherBuilder withFirstname(Firstname firstName) {
            this.firstName = firstName;
            return this;
        }

        TeacherBuilder withLastname(Lastname lastName) {
            this.lastName = lastName;
            return this;
        }

        TeacherBuilder withDegree(Degree degree) {
            this.degree = degree;
            return this;
        }

        TeacherBuilder withTeacherId(TeacherId teacherId) {
            this.teacherId = teacherId;
            return this;
        }

        Teacher build() {
            return new Teacher(firstName,
                               lastName,
                               degree,
                               teacherId);
        }
    }
}