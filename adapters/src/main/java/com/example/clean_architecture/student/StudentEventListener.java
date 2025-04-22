package com.example.clean_architecture.student;

import com.example.clean_architecture.student.event.StudentEvent;
import org.springframework.context.event.EventListener;

class StudentEventListener {
    private final StudentFacade studentFacade;

    public StudentEventListener(final StudentFacade studentFacade) {
        this.studentFacade = studentFacade;
    }

    @EventListener
    public void on(StudentEvent event) {
//        studentFacade.handle(event);
    }
}
