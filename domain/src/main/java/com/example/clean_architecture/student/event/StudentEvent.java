package com.example.clean_architecture.student.event;

import com.example.clean_architecture.DomainEvent;
import com.example.clean_architecture.course.vo.CourseSnapshot;
import com.example.clean_architecture.student.vo.Email;
import com.example.clean_architecture.student.vo.Firstname;
import com.example.clean_architecture.student.vo.Lastname;

import java.time.Instant;
import java.util.Set;

public class StudentEvent implements DomainEvent {

    public enum State {
        CREATED, UPDATED, DELETED
    }

    private final Long id;
    private final Instant occurredOn;
    private final StudentEvent.Data data;
    private final StudentEvent.State state;

    public StudentEvent(final Long id,
                        final StudentEvent.Data data,
                        final StudentEvent.State state) {

        this.id = id;
        this.occurredOn = Instant.now();
        this.data = data;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public StudentEvent.Data getData() {
        return data;
    }

    public StudentEvent.State getState() {
        return state;
    }

    @Override
    public Instant getOccurredOn() {
        return occurredOn;
    }

    public record Data(Firstname firstName,
                       Lastname lastName,
                       Email email,
                       Set<CourseSnapshot> courseSnapshots) {}
}