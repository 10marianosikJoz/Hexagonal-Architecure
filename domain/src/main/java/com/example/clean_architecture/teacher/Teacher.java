package com.example.clean_architecture.teacher;

import com.example.clean_architecture.teacher.vo.Degree;
import com.example.clean_architecture.teacher.vo.Firstname;
import com.example.clean_architecture.teacher.vo.Lastname;
import com.example.clean_architecture.teacher.vo.TeacherId;
import com.example.clean_architecture.teacher.vo.TeacherSnapshot;
import com.example.clean_architecture.teacher.vo.TeacherSourceId;

import java.util.Objects;

class Teacher {

    static Teacher restoreFromSnapshot(TeacherSnapshot snapshot) {
        return Teacher.builder()
                      .withFirstname(snapshot.getFirstName())
                      .withLastname(snapshot.getLastName())
                      .withDegree(snapshot.getDegree())
                      .withTeacherSourceId(snapshot.getTeacherSourceId())
                      .withTeacherId(snapshot.getTeacherId())
                      .build();
    }

    public static TeacherBuilder builder() {
        return new TeacherBuilder();
    }

    private Firstname firstName;
    private Lastname lastName;
    private Degree degree;
    private final TeacherSourceId teacherSourceId;
    private final TeacherId teacherId;

    public Teacher(final Firstname firstName,
                   final Lastname lastName,
                   final Degree degree,
                   final TeacherSourceId teacherSourceId,
                   final TeacherId teacherId) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.degree = degree;
        this.teacherId = Objects.requireNonNull(teacherId, "Identifier not provided");
        this.teacherSourceId = teacherSourceId;
    }

    void updateTeacherData(final Firstname firstName,
                           final Lastname lastName,
                           final Degree degree) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.degree = degree;
    }

    TeacherSnapshot getSnapshot() {
        return TeacherSnapshot.builder()
                              .withFirstname(firstName)
                              .withLastname(lastName)
                              .withDegree(degree)
                              .withTeacherSourceId(teacherSourceId)
                              .withTeacherId(teacherId)
                              .build();
    }

    public static class TeacherBuilder {

        private Firstname firstName;
        private Lastname lastName;
        private Degree degree;
        private TeacherId teacherId;
        private TeacherSourceId teacherSourceId;

        public TeacherBuilder withFirstname(Firstname firstName) {
            this.firstName = firstName;
            return this;
        }

        public TeacherBuilder withLastname(Lastname lastName) {
            this.lastName = lastName;
            return this;
        }

        public TeacherBuilder withDegree(Degree degree) {
            this.degree = degree;
            return this;
        }

        public TeacherBuilder withTeacherId(TeacherId teacherId) {
            this.teacherId = teacherId;
            return this;
        }

        public TeacherBuilder withTeacherSourceId(TeacherSourceId teacherSourceId) {
            this.teacherSourceId = teacherSourceId;
            return this;
        }

        Teacher build() {
            return new Teacher(firstName,
                               lastName,
                               degree,
                               teacherSourceId,
                               teacherId);
        }
    }
}
