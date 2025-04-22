package com.example.clean_architecture.teacher;

import com.example.clean_architecture.teacher.dto.QueryTeacherDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryTeacherRepository implements TeacherRepository, TeacherQueryRepository {

    private static final Map<Long, Teacher> DATABASE = new ConcurrentHashMap<>();

    void truncate() {
        DATABASE.clear();
    }

    @Override
    public List<QueryTeacherDto> findAll() {
        return DATABASE.values().stream()
                                .map(it -> new QueryTeacherDto(it.getSnapshot().getFirstName(),
                                                                       it.getSnapshot().getLastName(),
                                                                       it.getSnapshot().getDegree(),
                                                                       it.getSnapshot().getTeacherId())).toList();
    }

    @Override
    public Optional<QueryTeacherDto> findDtoByTeacherId(Long teacherId) {
        return DATABASE.get(teacherId) == null ? Optional.empty() : Optional.of(QueryTeacherDto.builder()
                                                                                               .withTeacherId(DATABASE.get(teacherId).getSnapshot().getTeacherId()).build());
    }

    @Override
    public Optional<Teacher> findByTeacherId(Long teacherId) {
        return DATABASE.get(teacherId) == null ? Optional.empty() : Optional.of(DATABASE.get(teacherId));
    }

    @Override
    public void deleteByTeacherId(Long teacherId) {
        DATABASE.remove(teacherId);
    }

    @Override
    public Teacher save(Teacher teacher) {
        DATABASE.put(teacher.getSnapshot().getTeacherId().teacherId(), teacher);
        return teacher;
    }
}