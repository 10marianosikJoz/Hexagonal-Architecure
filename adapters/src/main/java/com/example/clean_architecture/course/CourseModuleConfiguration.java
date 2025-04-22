package com.example.clean_architecture.course;

import com.example.clean_architecture.DomainEventPublisher;
import com.example.clean_architecture.student.StudentFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class CourseModuleConfiguration {

    @Bean
    CourseFacade courseFacade(final CourseRepository courseRepository,
                              final StudentFacade studentFacade,
                              final DomainEventPublisher domainEventPublisher) {

        return new CourseFacade(courseRepository,
                                studentFacade,
                                new CourseFactory(),
                                domainEventPublisher);
    }

    @Bean
    CourseEventListener courseEventListener(CourseFacade courseFacade) {
        return new CourseEventListener(courseFacade);
    }
}