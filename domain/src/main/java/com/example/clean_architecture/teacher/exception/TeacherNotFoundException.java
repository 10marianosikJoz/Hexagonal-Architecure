package com.example.clean_architecture.teacher.exception;

class TeacherNotFoundException extends BusinessTeacherException {

    TeacherNotFoundException(String message) {
        super(message);
    }
}
