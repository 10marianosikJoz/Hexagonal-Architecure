package com.example.clean_architecture.course;

import com.example.clean_architecture.course.dto.QueryCourseDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

class InMemoryCourseRepository implements CourseRepository, CourseQueryRepository {

    private static final Map<Long, Course> DATABASE = new ConcurrentHashMap<>();

    void truncate() {
        DATABASE.clear();
    }

    @Override
    public Optional<Course> findById(Long courseId) {
        return DATABASE.get(courseId) == null ? Optional.empty() : Optional.of(DATABASE.get(courseId));
    }

    @Override
    public List<Course> saveAll(Iterable<Course> entities) {
        return List.of();
    }

    @Override
    public void deleteById(Long id) {
        DATABASE.remove(id);
    }

    @Override
    public Course save(Course course) {
        DATABASE.put(course.getSnapshot().getCourseId().courseId(), course);
        return course;
    }

    @Override
    public <T> Set<T> findBy(Class<T> type) {
        return DATABASE.values().stream()
                                .map(type::cast)
                                .collect(Collectors.toSet());
    }

    @Override
    public Optional<QueryCourseDto> findDtoByCourseId(Long id) {
        return Optional.empty();
    }
}