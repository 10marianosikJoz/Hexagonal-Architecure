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
import com.example.clean_architecture.teacher.vo.TeacherSourceId;

import java.util.HashSet;
import java.util.Set;

public class QueryCourseDto {

    static public QueryCourseDtoBuilder builder() {
        return new QueryCourseDtoBuilder();
    }

    public static QueryCourseDto restoreFromCommandDto(final CommandCourseDto commandCourseDto) {
        return new QueryCourseDto(commandCourseDto.getCourseId(),
                commandCourseDto.getName(),
                commandCourseDto.getDescription(),
                commandCourseDto.getStartDate(),
                commandCourseDto.getEndDate(),
                commandCourseDto.getParticipantLimit(),
                commandCourseDto.getParticipantsNumber(),
                commandCourseDto.getStatus(),
                commandCourseDto.getTeacherSourceId());
    }

    private final CourseId courseId;
    private final Name name;
    private final Description description;
    private final StartDate startDate;
    private final EndDate endDate;
    private final ParticipantLimit participantLimit;
    private final ParticipantNumber participantNumber;
    private final Status status;
    private final TeacherSourceId teacherSourceId;
    private final Set<StudentSnapshot> students = new HashSet<>();

    public QueryCourseDto(final CourseId courseId,
                          final Name name,
                          final Description description,
                          final StartDate startDate,
                          final EndDate endDate,
                          final ParticipantLimit participantLimit,
                          final ParticipantNumber participantNumber,
                          final Status status,
                          final TeacherSourceId teacherSourceId) {

        this.courseId = courseId;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.participantLimit = participantLimit;
        this.participantNumber = participantNumber;
        this.status = status;
        this.teacherSourceId = teacherSourceId;
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

    public ParticipantNumber getParticipantsNumber() {
        return participantNumber;
    }

    public Status getStatus() {
        return status;
    }

    public TeacherSourceId getTeacherSourceId() {
        return teacherSourceId;
    }

    public Set<StudentSnapshot> getStudents() {
        return students;
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
        private TeacherSourceId teacherSourceId;

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

        public QueryCourseDtoBuilder withTeacher(TeacherSourceId sourceId) {
            this.teacherSourceId = sourceId;
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
                                      teacherSourceId);
        }
    }
}
