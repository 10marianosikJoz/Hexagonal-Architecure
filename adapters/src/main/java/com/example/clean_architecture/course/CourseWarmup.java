package com.example.clean_architecture.course;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component("courseWarmup")
class CourseWarmup implements ApplicationListener<ContextRefreshedEvent> {

    private final CourseInitializer initializer;

    CourseWarmup(final CourseRepository courseRepository,
                 final CourseQueryRepository courseQueryRepository) {

        this.initializer = new CourseInitializer(courseRepository,
                                                 courseQueryRepository);
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
//        initializer.init();
    }
}
