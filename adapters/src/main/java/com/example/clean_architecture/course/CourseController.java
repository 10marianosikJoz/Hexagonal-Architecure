package com.example.clean_architecture.course;

import com.example.clean_architecture.course.dto.CommandCourseDto;
import com.example.clean_architecture.course.dto.QueryCourseDto;
import com.example.clean_architecture.course.exception.BusinessCourseException;
import com.example.clean_architecture.student.dto.QueryStudentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/courses")
class CourseController {

    private final CourseFacade courseFacade;
    private final CourseQueryRepository courseQueryRepository;

    CourseController(final CourseFacade courseFacade,
                     final CourseQueryRepository courseQueryRepository) {

        this.courseFacade = courseFacade;
        this.courseQueryRepository = courseQueryRepository;
    }

    @GetMapping("/{courseId}")
    ResponseEntity<QueryCourseDto> getCourseById(@PathVariable Long courseId) {
        return courseQueryRepository.findDtoByCourseId(courseId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> BusinessCourseException.notFound("Course with id: " + courseId + " not found"));
    }

    @DeleteMapping("/{courseId}")
    ResponseEntity<CommandCourseDto> deleteCourseById(@PathVariable Long courseId) {
        courseFacade.deleteCourseById(courseId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    List<QueryCourseDto> getAllCourses() {
        return new ArrayList<>(courseQueryRepository.findBy(QueryCourseDto.class));
    }

    @PostMapping
    ResponseEntity<?> addNewCourse(@RequestBody CommandCourseDto course) {
        courseFacade.addNewCourse(course);
        var persisted = QueryCourseDto.restoreFromCommandDto(course);

        return ResponseEntity.created(URI.create("/" + persisted.courseId())).body(persisted);
    }

    @PostMapping("/{courseId}/student/{studentId}")
    ResponseEntity<CommandCourseDto> courseEnrollment(@PathVariable Long courseId, @PathVariable Long studentId) {
        courseFacade.courseEnrollment(courseId, studentId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{courseId}/members")
    List<QueryStudentDto> courseMembers(@PathVariable Long courseId) {
        return courseFacade.getCourseMembers(courseId);
    }
}