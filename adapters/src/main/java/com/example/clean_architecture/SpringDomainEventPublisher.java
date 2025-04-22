package com.example.clean_architecture;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
class SpringDomainEventPublisher implements DomainEventPublisher {

    private final ApplicationEventPublisher innerPublisher;

    SpringDomainEventPublisher(final ApplicationEventPublisher publisher) {
        this.innerPublisher = publisher;
    }

    @Override
    public void publish(DomainEvent event) {
        innerPublisher.publishEvent(event);
    }
}