package com.example.clean_architecture.student;

import com.example.clean_architecture.student.dto.QueryStudentDto;
import com.example.clean_architecture.student.dto.CommandStudentDto;
import com.example.clean_architecture.student.exception.BusinessStudentException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/students")
class StudentController {

    private final StudentFacade studentFacade;
    private final StudentQueryRepository studentQueryRepository;

    StudentController(final StudentFacade studentFacade,
                      final StudentQueryRepository studentQueryRepository) {

        this.studentFacade = studentFacade;
        this.studentQueryRepository = studentQueryRepository;
    }

    @GetMapping("/{studentId}")
    ResponseEntity<QueryStudentDto> getStudentById(@PathVariable Long studentId) {
        return studentQueryRepository.findDtoByStudentIdValue(studentId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> BusinessStudentException.notFound("Student with id: " + studentId + " not found"));
    }

    @DeleteMapping("/{studentId}")
    ResponseEntity<CommandStudentDto> deleteStudentById(@PathVariable Long studentId) {
        studentFacade.deleteStudentById(studentId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    List<QueryStudentDto> getAllStudents() {
        return new ArrayList<>(studentQueryRepository.findBy(QueryStudentDto.class));
    }

    @PostMapping
    ResponseEntity<?> addNewUser(@RequestBody CommandStudentDto student) {
        studentFacade.addNewStudent(student);
        var persisted = QueryStudentDto.restoreFromCommandDto(student);

        return ResponseEntity.created(URI.create("/" + persisted.studentId())).body(persisted);
    }

    @PutMapping("/{studentId}")
    ResponseEntity<CommandStudentDto> updateStudent(@PathVariable Long studentId, @RequestBody CommandStudentDto student) {
        studentFacade.updateStudent(studentId,student);

        return ResponseEntity.noContent().build();
    }
}