package com.example.clean_architecture.course.exception;

 class CourseLimitExceededException extends BusinessCourseException {

     CourseLimitExceededException(String message) {
        super(message);
    }
}