package com.example.clean_architecture.student.exception;

 class StudentNotFoundException extends BusinessStudentException {

     StudentNotFoundException(String message) {
        super(message);
    }
}