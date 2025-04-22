package com.example.clean_architecture.teacher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class TeacherModuleConfiguration {

    @Bean
    TeacherFacade teacherFacadeBean(final TeacherRepository teacherRepository) {
        return new TeacherFacade(teacherRepository, new TeacherFactory());
    }
}