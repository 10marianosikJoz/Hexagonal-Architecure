package com.example.clean_architecture.student;

import com.example.clean_architecture.student.exception.BusinessStudentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
 class StudentExceptionHandler {

    @ExceptionHandler(BusinessStudentException.class)
    ResponseEntity<ErrorDetails> handleBusinessException(BusinessStudentException e, WebRequest request) {
        var errorDetails = new ErrorDetails(e.getMessage(),request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    record ErrorDetails(String message, String responseInformation) {}
}