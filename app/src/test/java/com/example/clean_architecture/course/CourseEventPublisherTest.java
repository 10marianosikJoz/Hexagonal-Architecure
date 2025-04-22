package com.example.clean_architecture.course;

import com.example.clean_architecture.DomainEvent;
import com.example.clean_architecture.DomainEventPublisher;

import java.util.ArrayList;
import java.util.List;

class CourseEventPublisherTest implements DomainEventPublisher {

    private final List<DomainEvent> events = new ArrayList<>();

    @Override
    public void publish(DomainEvent event) {
        events.add(event);
    }

    List<DomainEvent> getEvents() {
        return events;
    }
}