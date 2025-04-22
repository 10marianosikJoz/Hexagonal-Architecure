package com.example.clean_architecture.course.exception;

 class CourseStudentEnrollmentException extends BusinessCourseException {

     CourseStudentEnrollmentException(String message) {
        super(message);
    }
}