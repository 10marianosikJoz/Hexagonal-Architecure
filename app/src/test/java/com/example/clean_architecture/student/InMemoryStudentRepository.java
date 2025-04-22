package com.example.clean_architecture.student;

import com.example.clean_architecture.student.dto.QueryStudentDto;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

class InMemoryStudentRepository implements StudentRepository, StudentQueryRepository {

    private static final Map<Long, Student> DATABASE = new ConcurrentHashMap<>();

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
        return DATABASE.get(studentId) == null ? Optional.empty() : Optional.of(QueryStudentDto.builder()
                                                                                               .withStudentId(DATABASE.get(studentId).getSnapshot().getStudentId()).build());
    }

    @Override
    public Optional<Student> findById(Long studentId) {
        return DATABASE.get(studentId) == null ? Optional.empty() : Optional.of(DATABASE.get(studentId));
    }

    @Override
    public void deleteById(Long id) {
        DATABASE.remove(id);
    }

    @Override
    public Student save(Student student) {
        DATABASE.put(student.getSnapshot().getStudentId().value(), student);
        return student;
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(DATABASE.values());
    }

    @Override
    public List<Student> findAllByEmailIn(List<String> emails) {
        return DATABASE.values().stream()
                                .filter(it -> emails.contains(it.getSnapshot().getEmail().value()))
                                .collect(Collectors.toList());
    }
}