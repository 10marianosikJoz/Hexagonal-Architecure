package com.example.clean_architecture.course.dto;

import com.example.clean_architecture.course.Status;
import com.example.clean_architecture.course.vo.CourseId;
import com.example.clean_architecture.course.vo.Description;
import com.example.clean_architecture.course.vo.EndDate;
import com.example.clean_architecture.course.vo.Name;
import com.example.clean_architecture.course.vo.ParticipantLimit;
import com.example.clean_architecture.course.vo.ParticipantNumber;
import com.example.clean_architecture.course.vo.StartDate;
import com.example.clean_architecture.student.vo.StudentSnapshot;
import com.example.clean_architecture.teacher.vo.TeacherId;

import java.util.Set;

public record CommandCourseDto(CourseId courseId,
                               Name name,
                               Description description,
                               StartDate startDate,
                               EndDate endDate,
                               ParticipantLimit participantLimit,
                               ParticipantNumber participantNumber,
                               Status status,
                               TeacherId teacherId,
                               Set<StudentSnapshot> students) {

    static public CommandCourseDtoBuilder builder() {
        return new CommandCourseDtoBuilder();
    }

    public CommandCourseDto(CourseId courseId,
                            Name name,
                            Description description,
                            StartDate startDate,
                            EndDate endDate,
                            ParticipantLimit participantLimit,
                            ParticipantNumber participantNumber,
                            Status status,
                            TeacherId teacherId,
                            Set<StudentSnapshot> students) {

        this.courseId = courseId;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.participantLimit = participantLimit;
        this.participantNumber = participantNumber;
        this.status = status;
        this.teacherId = teacherId;
        this.students = Set.copyOf(students);
    }

    public static class CommandCourseDtoBuilder {

        private CourseId courseId;
        private Name name;
        private Description description;
        private StartDate startDate;
        private EndDate endDate;
        private ParticipantLimit participantLimit;
        private ParticipantNumber participantNumber;
        private Status status;
        private TeacherId teacherId;
        private Set<StudentSnapshot> students;

        public CommandCourseDtoBuilder withCourseId(CourseId courseId) {
            this.courseId = courseId;
            return this;
        }

        public CommandCourseDtoBuilder withName(Name name) {
            this.name = name;
            return this;
        }

        public CommandCourseDtoBuilder withDescription(Description description) {
            this.description = description;
            return this;
        }

        public CommandCourseDtoBuilder withStartDate(StartDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public CommandCourseDtoBuilder withEndDate(EndDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public CommandCourseDtoBuilder withParticipantLimit(ParticipantLimit participantLimit) {
            this.participantLimit = participantLimit;
            return this;
        }

        public CommandCourseDtoBuilder withParticipantNumber(ParticipantNumber participantNumber) {
            this.participantNumber = participantNumber;
            return this;
        }

        public CommandCourseDtoBuilder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public CommandCourseDtoBuilder withTeacher(TeacherId teacherId) {
            this.teacherId = teacherId;
            return this;
        }

        public CommandCourseDtoBuilder withStudents(Set<StudentSnapshot> students) {
            this.students = Set.copyOf(students);
            return this;
        }

        public CommandCourseDto build() {
            return new CommandCourseDto(courseId,
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