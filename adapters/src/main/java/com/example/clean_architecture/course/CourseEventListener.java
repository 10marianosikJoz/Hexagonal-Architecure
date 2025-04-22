package com.example.clean_architecture.course;

import com.example.clean_architecture.student.event.StudentEvent;
import org.springframework.context.event.EventListener;

class CourseEventListener {

    private final CourseFacade courseFacade;

    CourseEventListener(final CourseFacade courseFacade) {
        this.courseFacade = courseFacade;
    }

    @EventListener
    void on(StudentEvent event) {
        courseFacade.handleEvent(event);
    }
}