package com.example.clean_architecture.course;

import com.example.clean_architecture.course.dto.CommandCourseDto;
import com.example.clean_architecture.course.vo.CourseSnapshot;

class CourseFactory {

    Course from(CommandCourseDto source) {
        return Course.restoreFromSnapshot(CourseSnapshot.builder()
                                   .withCourseId(source.courseId())
                                   .withName(source.name())
                                   .withDescription(source.description())
                                   .withStartDate(source.startDate())
                                   .withEndDate(source.endDate())
                                   .withParticipantLimit(source.participantLimit())
                                   .withParticipantNumber(source.participantNumber())
                                   .withStatus(source.status())
                                   .withTeacherId(source.teacherId())
                                   .withStudents(source.students())
                                   .build());
    }
}