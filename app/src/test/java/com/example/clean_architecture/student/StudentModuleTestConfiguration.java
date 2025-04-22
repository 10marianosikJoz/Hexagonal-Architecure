package com.example.clean_architecture.student;

public class StudentModuleTestConfiguration {

    public StudentFacade studentFacade() {
        return new StudentFacade(new InMemoryStudentRepository(),
                                 new InMemoryStudentQueryRepository(),
                                 new StudentFactory(),
                                 new StudentEventPublisherTest());
    }
}