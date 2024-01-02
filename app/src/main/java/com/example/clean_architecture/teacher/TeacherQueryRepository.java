package com.example.clean_architecture.teacher;

import com.example.clean_architecture.teacher.dto.QueryTeacherDto;
import com.example.clean_architecture.teacher.vo.TeacherSnapshot;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TeacherQueryRepository  {

    long count();

    <T> Set<T> findBy(Class<T> type);

    List<QueryTeacherDto> findAllBy();

    Optional<QueryTeacherDto> findDtoByTeacherIdValue(Long teacherId);

    Optional<TeacherSnapshot> findByTeacherIdValue(Long teacherId);
}
