package com.example.clean_architecture.course;

import com.example.clean_architecture.DomainEventPublisher;
import com.example.clean_architecture.course.vo.CourseSnapshot;
import com.example.clean_architecture.student.StudentFacade;
import com.example.clean_architecture.student.StudentQueryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CourseConfiguration {

    @Bean
    CourseFacade courseFacade(final CourseRepository courseRepository,
                              final StudentFacade studentFacade,
                              final StudentQueryRepository studentQueryRepository,
                              final DomainEventPublisher domainEventPublisher) {

        return new CourseFacade(courseRepository,
                                studentFacade,
                                new CourseFactory(),
                                studentQueryRepository,
                                domainEventPublisher);
    }

    @Bean
    CourseSnapshot courseSnapshot() {
        return new CourseSnapshot();
    }
}
