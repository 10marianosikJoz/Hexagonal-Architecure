package com.example.clean_architecture.teacher.exception;

 class TeacherException extends RuntimeException {

    TeacherException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}