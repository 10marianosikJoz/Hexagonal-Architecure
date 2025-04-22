package com.example.clean_architecture.student;

import com.example.clean_architecture.course.vo.CourseId;
import com.example.clean_architecture.course.vo.CourseSnapshot;
import com.example.clean_architecture.student.dto.QueryStudentDto;
import com.example.clean_architecture.student.vo.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

class InMemoryStudentQueryRepository implements StudentQueryRepository {

    private static final Map<Long, QueryStudentDto> DATABASE = new ConcurrentHashMap<>();

    void truncate() {
        DATABASE.clear();
    }

    @Override
    public <T> Set<T> findBy(Class<T> type) {
        return DATABASE.values().stream()
                                .map(type::cast)
                                .collect(Collectors.toSet());
    }

    @Override
    public Optional<QueryStudentDto> findDtoByStudentIdValue(Long studentId) {
        DATABASE.put(1L, prepareQueryStudentDto());
        return DATABASE.get(studentId) == null ? Optional.empty() : Optional.of(DATABASE.get(studentId));
    }

    private QueryStudentDto prepareQueryStudentDto() {
        return QueryStudentDto.builder()
                              .withStudentId(new StudentId(1L))
                              .withFirstname(new Firstname("John"))
                              .withLastname(new Lastname("Murphy"))
                              .withEmail(new Email("john@gmail.com"))
                              .withStatus(StudentSnapshot.Status.ACTIVE)
                              .withCourses(new HashSet<>())
                              .build();
    }
}