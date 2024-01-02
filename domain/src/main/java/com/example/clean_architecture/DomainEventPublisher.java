package com.example.clean_architecture;

public interface DomainEventPublisher {

    void publish(DomainEvent event);
}
