package com.example.clean_architecture.teacher;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component("teacherWarmup")
class TeacherWarmup implements ApplicationListener<ContextRefreshedEvent> {

    private final TeacherInitializer initializer;

    TeacherWarmup(final TeacherRepository teacherRepository,
                  final TeacherQueryRepository teacherQueryRepository) {

        this.initializer = new TeacherInitializer(teacherRepository, teacherQueryRepository);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
//        initializer.init();
    }
}
