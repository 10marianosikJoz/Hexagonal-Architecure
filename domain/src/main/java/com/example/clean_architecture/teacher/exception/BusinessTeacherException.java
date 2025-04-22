package com.example.clean_architecture.teacher.exception;

public class BusinessTeacherException extends TeacherException {

    public static BusinessTeacherException notFound(String message) {
        return new TeacherNotFoundException(message);
    }

    public BusinessTeacherException(String message) {
        super(message);
    }
}