package com.example.clean_architecture.teacher;

import com.example.clean_architecture.teacher.exception.BusinessTeacherException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
 class TeacherExceptionHandler {

    @ExceptionHandler(BusinessTeacherException.class)
    ResponseEntity<ErrorDetails> handleTeacherBusinessException(BusinessTeacherException e, WebRequest request) {
        var errorDetails = new ErrorDetails(e.getMessage(),request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    record ErrorDetails(String message, String responseInformation) {}
}