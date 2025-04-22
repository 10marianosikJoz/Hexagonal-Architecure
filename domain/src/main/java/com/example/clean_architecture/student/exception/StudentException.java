package com.example.clean_architecture.student.exception;

 class StudentException extends RuntimeException {

    StudentException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}