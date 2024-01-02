package com.example.clean_architecture.student;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component("studentWarmup")
 class StudentWarmup implements ApplicationListener<ContextRefreshedEvent> {

    private final StudentInitializer initializer;

    StudentWarmup(final StudentRepository studentRepository,
                  final StudentQueryRepository studentQueryRepository) {

        this.initializer = new StudentInitializer(studentRepository, studentQueryRepository);
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
//        initializer.init();
    }
}
