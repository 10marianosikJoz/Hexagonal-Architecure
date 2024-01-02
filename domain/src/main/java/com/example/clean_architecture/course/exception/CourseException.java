package com.example.clean_architecture.course.exception;

 class CourseException extends RuntimeException {

    CourseException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
