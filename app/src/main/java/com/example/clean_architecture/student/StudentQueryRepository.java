package com.example.clean_architecture.student;

import com.example.clean_architecture.student.dto.QueryStudentDto;

import java.util.Optional;
import java.util.Set;

public interface StudentQueryRepository {

    long count();

    <T> Set<T> findBy(Class<T> type);

    Optional<QueryStudentDto> findDtoByStudentIdValue(Long studentId);
}
