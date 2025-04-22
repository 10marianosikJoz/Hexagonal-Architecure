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

public record QueryCourseDto(CourseId courseId,
                             Name name,
                             Description description,
                             StartDate startDate,
                             EndDate endDate,
                             ParticipantLimit participantLimit,
                             ParticipantNumber participantNumber,
                             Status status,
                             TeacherId teacherId,
                             Set<StudentSnapshot> students) {

    static public QueryCourseDtoBuilder builder() {
        return new QueryCourseDtoBuilder();
    }

    public static QueryCourseDto restoreFromCommandDto(CommandCourseDto commandCourseDto) {
        return new QueryCourseDto(commandCourseDto.courseId(),
                                  commandCourseDto.name(),
                                  commandCourseDto.description(),
                                  commandCourseDto.startDate(),
                                  commandCourseDto.endDate(),
                                  commandCourseDto.participantLimit(),
                                  commandCourseDto.participantNumber(),
                                  commandCourseDto.status(),
                                  commandCourseDto.teacherId(),
                                  commandCourseDto.students());
    }

    public QueryCourseDto(CourseId courseId,
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

    public static class QueryCourseDtoBuilder {

        private CourseId id;
        private Name name;
        private Description description;
        private StartDate startDate;
        private EndDate endDate;
        private ParticipantLimit participantLimit;
        private ParticipantNumber participantNumber;
        private Status status;
        private TeacherId teacherId;
        private Set<StudentSnapshot> students;

        public QueryCourseDtoBuilder withCourseId(CourseId id) {
            this.id = id;
            return this;
        }

        public QueryCourseDtoBuilder withName(Name name) {
            this.name = name;
            return this;
        }

        public QueryCourseDtoBuilder withDescription(Description description) {
            this.description = description;
            return this;
        }

        public QueryCourseDtoBuilder withStartDate(StartDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public QueryCourseDtoBuilder withEndDate(EndDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public QueryCourseDtoBuilder withParticipantLimit(ParticipantLimit participantLimit) {
            this.participantLimit = participantLimit;
            return this;
        }

        public QueryCourseDtoBuilder withParticipantNumber(ParticipantNumber participantNumber) {
            this.participantNumber = participantNumber;
            return this;
        }

        public QueryCourseDtoBuilder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public QueryCourseDtoBuilder withTeacher(TeacherId teacherId) {
            this.teacherId = teacherId;
            return this;
        }

        public QueryCourseDtoBuilder withStudents(Set<StudentSnapshot> students) {
            this.students = Set.copyOf(students);
            return this;
        }

        public QueryCourseDto build() {
            return new QueryCourseDto(id,
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