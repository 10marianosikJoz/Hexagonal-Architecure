package com.example.clean_architecture.course.vo;

import com.example.clean_architecture.teacher.exception.BusinessTeacherException;

public record ParticipantLimit(Long participantLimit) {

    public ParticipantLimit {
        if (participantLimit == null) {
            throw new BusinessTeacherException("ParticipantLimit is not provided.");
        }
    }
}