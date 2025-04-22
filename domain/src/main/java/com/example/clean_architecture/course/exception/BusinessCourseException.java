package com.example.clean_architecture.course.exception;

public class BusinessCourseException extends CourseException {

    public static BusinessCourseException notFound(String message) {
        return new CourseNotFoundException(message);
    }

    public static BusinessCourseException enrollment(String message) {
        return new CourseStudentEnrollmentException(message);
    }

    public static BusinessCourseException limitExceeded(String message) {
        return new CourseLimitExceededException(message);
    }

    public BusinessCourseException(String message) {
        super(message);
    }
}