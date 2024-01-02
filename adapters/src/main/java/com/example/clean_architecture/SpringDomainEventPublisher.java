package com.example.clean_architecture;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class SpringDomainEventPublisher implements DomainEventPublisher {

    private final ApplicationEventPublisher innerPublisher;

    public SpringDomainEventPublisher(final ApplicationEventPublisher publisher) {
        this.innerPublisher = publisher;
    }

    @Override
    public void publish(final DomainEvent event) {
        innerPublisher.publishEvent(event);
    }
}
