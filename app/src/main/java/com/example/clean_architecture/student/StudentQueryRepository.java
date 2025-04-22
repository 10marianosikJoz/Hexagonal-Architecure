package com.example.clean_architecture.student;

import com.example.clean_architecture.student.dto.QueryStudentDto;

import java.util.Optional;
import java.util.Set;

interface StudentQueryRepository {

    <T> Set<T> findBy(Class<T> type);

    Optional<QueryStudentDto> findDtoByStudentIdValue(Long studentId);
}