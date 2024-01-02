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
import java.util.UUID;

import static com.example.clean_architecture.course.Status.*;

public class CommandCourseDto {

    static public CommandCourseDtoBuilder builder() {
        return new CommandCourseDtoBuilder();
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

    public CommandCourseDto(final Name name,
                            final Description description,
                            final StartDate startDate,
                            final EndDate endDate,
                            final ParticipantLimit participantLimit,
                            final ParticipantNumber participantNumber,
                            final TeacherSourceId teacherSourceId) {

        this.courseId = new CourseId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.participantLimit = participantLimit;
        this.participantNumber = participantNumber;
        this.status = ACTIVE;
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

    public static class CommandCourseDtoBuilder {

        private CourseId courseId;
        private Name name;
        private Description description;
        private StartDate startDate;
        private EndDate endDate;
        private ParticipantLimit participantLimit;
        private ParticipantNumber participantNumber;
        private Status status;
        private TeacherSourceId teacherSourceId;

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

        public CommandCourseDtoBuilder withTeacher(TeacherSourceId sourceId) {
            this.teacherSourceId = sourceId;
            return this;
        }

        public CommandCourseDto build() {
            return new CommandCourseDto(name,
                                        description,
                                        startDate,
                                        endDate,
                                        participantLimit,
                                        participantNumber,
                                        teacherSourceId);
        }
    }
}
