package com.example.clean_architecture;

import java.time.Instant;

public interface DomainEvent {

    Instant getOccurredOn();
}