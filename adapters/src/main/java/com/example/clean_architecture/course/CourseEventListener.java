package com.example.clean_architecture.course;

import com.example.clean_architecture.student.vo.StudentEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class CourseEventListener {

    private final CourseFacade courseFacade;

    public CourseEventListener(final CourseFacade courseFacade) {
        this.courseFacade = courseFacade;
    }

    @EventListener
    public void on(StudentEvent event) {
        courseFacade.handleEvent(event);
    }
}
