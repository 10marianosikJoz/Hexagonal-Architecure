package com.example.clean_architecture.teacher;

import com.example.clean_architecture.teacher.dto.QueryTeacherDto;

import java.util.List;
import java.util.Optional;

interface TeacherQueryRepository  {

    List<QueryTeacherDto> findAll();

    Optional<QueryTeacherDto> findDtoByTeacherId(Long teacherId);
}