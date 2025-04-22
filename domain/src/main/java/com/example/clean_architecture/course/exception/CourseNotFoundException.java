package com.example.clean_architecture.course.exception;

 class CourseNotFoundException extends BusinessCourseException {

     CourseNotFoundException(String message) {
         super(message); }
}