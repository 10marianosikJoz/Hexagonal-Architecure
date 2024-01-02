package com.example.clean_architecture.course;

import com.example.clean_architecture.course.dto.CommandCourseDto;
import com.example.clean_architecture.course.vo.CourseSnapshot;


class CourseFactory {

    Course from(final CommandCourseDto source) {
        var course = CourseSnapshot.builder()
                                   .withCourseId(source.getCourseId())
                                   .withName(source.getName())
                                   .withDescription(source.getDescription())
                                   .withStartDate(source.getStartDate())
                                   .withEndDate(source.getEndDate())
                                   .withParticipantLimit(source.getParticipantLimit())
                                   .withParticipantNumber(source.getParticipantsNumber())
                                   .withStatus(source.getStatus())
                                   .withTeacherSourceId(source.getTeacherSourceId())
                                   .withStudents(source.getStudents())
                                   .build();

        return Course.restoreFromSnapshot(course);
    }
}
