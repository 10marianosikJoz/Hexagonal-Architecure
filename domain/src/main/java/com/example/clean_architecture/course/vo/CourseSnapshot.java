package com.example.clean_architecture.course.vo;

import com.example.clean_architecture.course.Status;
import com.example.clean_architecture.student.vo.StudentSnapshot;
import com.example.clean_architecture.teacher.vo.TeacherId;

import java.util.HashSet;
import java.util.Set;

public class CourseSnapshot {

    public static CourseSnapshotBuilder builder() {
        return new CourseSnapshotBuilder();
    }

    private final CourseId courseId;
    private final Name name;
    private final Description description;
    private final StartDate startDate;
    private final EndDate endDate;
    private final ParticipantLimit participantLimit;
    private final ParticipantNumber participantNumber;
    private Status status;
    private final TeacherId teacherId;
    private final Set<StudentSnapshot> students;

    public CourseSnapshot(final CourseId courseId,
                          final Name name,
                          final Description description,
                          final StartDate startDate,
                          final EndDate endDate,
                          final ParticipantLimit participantLimit,
                          final ParticipantNumber participantNumber,
                          final Status status,
                          final TeacherId teacherId,
                          final Set<StudentSnapshot> students) {

        this.courseId = courseId;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.participantLimit = participantLimit;
        this.participantNumber = participantNumber;
        this.status = status;
        this.teacherId = teacherId;
        this.students = students;
    }

    public CourseId getCourseId() {
        return courseId;
    }

    public Name getName() {
        return name;
    }

    public Description getDescription() {
        return description;
    }

    public StartDate getStartDate() {
        return startDate;
    }

    public EndDate getEndDate() {
        return endDate;
    }

    public ParticipantLimit getParticipantLimit() {
        return participantLimit;
    }

    public ParticipantNumber getParticipantNumber() {
        return participantNumber;
    }

    public Status getStatus() {
        return status;
    }

    public TeacherId getTeacherId() {
        return teacherId;
    }

    public Set<StudentSnapshot> getStudents() {
        return students;
    }

    public void changeToActiveStatus() {
        this.status = Status.ACTIVE;
    }

    public static class CourseSnapshotBuilder {

        private CourseId courseId;
        private Name name;
        private Description description;
        private StartDate startDate;
        private EndDate endDate;
        private ParticipantLimit participantLimit;
        private ParticipantNumber participantNumber;
        private Status status;
        private TeacherId teacherId;
        private Set<StudentSnapshot> students = new HashSet<>();

        private CourseSnapshotBuilder() {}

        public CourseSnapshotBuilder withCourseId(CourseId courseId) {
            this.courseId = courseId;
            return this;
        }

        public CourseSnapshotBuilder withName(Name name) {
            this.name = name;
            return this;
        }

        public CourseSnapshotBuilder withDescription(Description description) {
            this.description = description;
            return this;
        }

        public CourseSnapshotBuilder withStartDate(StartDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public CourseSnapshotBuilder withEndDate(EndDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public CourseSnapshotBuilder withParticipantLimit(ParticipantLimit participantLimit) {
            this.participantLimit = participantLimit;
            return this;
        }

        public CourseSnapshotBuilder withParticipantNumber(ParticipantNumber participantNumber) {
            this.participantNumber = participantNumber;
            return this;
        }

        public CourseSnapshotBuilder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public CourseSnapshotBuilder withTeacherId(TeacherId teacherId) {
            this.teacherId = teacherId;
            return this;
        }

        public CourseSnapshotBuilder withStudents(Set<StudentSnapshot> students) {
            this.students = students;
            return this;
        }

        public CourseSnapshot build() {
            return new CourseSnapshot(courseId,
                                      name,
                                      description,
                                      startDate,
                                      endDate,
                                      participantLimit,
                                      participantNumber,
                                      status,
                                      teacherId,
                                      students);
        }
    }
}