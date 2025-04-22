package com.example.clean_architecture.student.exception;

 class StudentNotActiveException extends BusinessStudentException {

    StudentNotActiveException(String message) {
        super(message);
    }
}