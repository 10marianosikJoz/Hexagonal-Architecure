package com.example.clean_architecture.course.vo;

import com.example.clean_architecture.teacher.exception.BusinessTeacherException;

public record ParticipantNumber(Long participantNumber) {

    public ParticipantNumber {
        if (participantNumber == null) {
            throw new BusinessTeacherException("ParticipantNumber is not provided");
        }
    }

    public ParticipantNumber increaseNumberOfParticipants() {
        return new ParticipantNumber(participantNumber + 1);
    }

    public ParticipantNumber decreaseNumberOfParticipants() {
         return new ParticipantNumber(participantNumber - 1);
    }
}