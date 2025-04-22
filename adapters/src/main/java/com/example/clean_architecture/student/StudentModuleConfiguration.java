package com.example.clean_architecture.student;

import com.example.clean_architecture.DomainEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class StudentModuleConfiguration {

    @Bean
    StudentFacade studentFacade(final StudentRepository studentRepository,
                                final StudentQueryRepository studentQueryRepository,
                                final DomainEventPublisher publisher) {

        return new StudentFacade(studentRepository,
                                 studentQueryRepository,
                                 new StudentFactory(),
                                 publisher);
    }
}