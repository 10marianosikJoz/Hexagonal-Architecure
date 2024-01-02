package com.example.clean_architecture.teacher;

import com.example.clean_architecture.teacher.dto.CommandTeacherDto;
import com.example.clean_architecture.teacher.dto.QueryTeacherDto;
import com.example.clean_architecture.teacher.exception.BusinessTeacherException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/teachers")
class TeacherController {

    private final TeacherFacade teacherFacade;
    private final TeacherQueryRepository teacherQueryRepository;

    TeacherController(final TeacherFacade teacherFacade,
                      final TeacherQueryRepository teacherQueryRepository) {

        this.teacherFacade = teacherFacade;
        this.teacherQueryRepository = teacherQueryRepository;
    }

    @GetMapping("/{teacherId}")
    ResponseEntity<QueryTeacherDto> getTeacherById(@PathVariable Long teacherId) {
        return teacherQueryRepository.findDtoByTeacherIdValue(teacherId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> BusinessTeacherException.notFound("Teacher with id: " + teacherId + " not found"));
    }

    @DeleteMapping("/{teacherId}")
    ResponseEntity<CommandTeacherDto> deleteTeacherById(@PathVariable Long teacherId) {
        teacherFacade.deleteTeacherById(teacherId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    List<QueryTeacherDto> getAllTeachers() {
        return teacherQueryRepository.findAllBy();
    }

    @PostMapping
    ResponseEntity<?> addNewTeacher(@RequestBody CommandTeacherDto teacher) {
        teacherFacade.addNewTeacher(teacher);
        var persisted = QueryTeacherDto.restoreFromCommandDto(teacher);
        return ResponseEntity.created(URI.create("/" + teacher.getTeacherId())).body(persisted);
    }

    @PutMapping("/{teacherId}")
    ResponseEntity<CommandTeacherDto> updateTeacher(@PathVariable Long teacherId, @RequestBody CommandTeacherDto teacherDto) {
        teacherFacade.updateTeacher(teacherId, teacherDto);
        return ResponseEntity.noContent().build();
    }
}
