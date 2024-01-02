package com.example.clean_architecture.student;

import com.example.clean_architecture.DomainEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class StudentConfiguration {
    @Bean
    StudentFacade studentFacade(final StudentRepository studentRepository,
                                final DomainEventPublisher publisher) {

        return new StudentFacade(studentRepository,
                                 new StudentFactory(),
                                 publisher);
    }
}
