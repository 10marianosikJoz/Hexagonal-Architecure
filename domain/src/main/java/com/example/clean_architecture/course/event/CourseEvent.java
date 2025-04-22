package com.example.clean_architecture.course.event;

import com.example.clean_architecture.DomainEvent;
import com.example.clean_architecture.course.vo.Description;
import com.example.clean_architecture.course.vo.EndDate;
import com.example.clean_architecture.course.vo.Name;
import com.example.clean_architecture.course.vo.StartDate;

import java.time.Instant;

public class CourseEvent implements DomainEvent {

    public enum State {
        CREATED, UPDATED, DELETED
    }

    private final Long id;
    private final Instant occurredOn;
    private final Data data;
    private final State state;

    public CourseEvent(final Long id,
                       final Data data,
                       final State state) {

        this.id = id;
        this.occurredOn = Instant.now();
        this.data = data;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public Data getData() {
        return data;
    }

    public State getState() {
        return state;
    }

    @Override
    public Instant getOccurredOn() {
        return occurredOn;
    }

    public record Data(Name name,
                       Description description,
                       StartDate startDate,
                       EndDate endDate) {}
}