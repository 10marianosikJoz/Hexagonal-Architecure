package com.example.clean_architecture.course.vo;

import com.example.clean_architecture.course.Status;
import com.example.clean_architecture.teacher.vo.TeacherSnapshot;

public record CourseCreator(Name name,
                            Description description,
                            StartDate startDate,
                            EndDate endDate,
                            ParticipantLimit participantLimit,
                            ParticipantNumber participantNumber,
                            Status status,
                            TeacherSnapshot teacherSnapshot) {}