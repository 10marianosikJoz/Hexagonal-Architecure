package com.example.clean_architecture.student.exception;

public class BusinessStudentException extends StudentException {

    public static BusinessStudentException notActive(String message) {
        return new StudentNotActiveException(message);
    }

    public static BusinessStudentException notFound(String message) {
        return new StudentNotFoundException(message);
    }

    public BusinessStudentException(String message) {
        super(message);
    }
}