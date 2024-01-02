package com.example.clean_architecture.course.exception;

 class CourseNotActiveException extends BusinessCourseException {

     CourseNotActiveException(String message) {
        super(message);
    }
}
