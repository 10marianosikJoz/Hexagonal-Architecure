package com.example.clean_architecture.course;

import com.example.clean_architecture.course.vo.CourseId;
import com.example.clean_architecture.course.vo.CourseSnapshot;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

interface SqlCourseQueryRepository extends CourseQueryRepository, Repository<CourseSnapshot, CourseId> {}

interface SqlCourseRepository extends Repository<CourseSnapshot, CourseId> {

    Optional<CourseSnapshot> findByCourseIdValue(Long courseId);

    List<CourseSnapshot> saveAll(Iterable<CourseSnapshot> entities);

    void deleteByCourseIdValue(Long id);

    CourseSnapshot save(CourseSnapshot entity);
}

@org.springframework.stereotype.Repository
class CourseRepositoryImpl implements CourseRepository {
    private final SqlCourseRepository sqlCourseRepository;

    CourseRepositoryImpl(final SqlCourseRepository sqlCourseRepository) {
        this.sqlCourseRepository = sqlCourseRepository;
    }

    @Override
    public Optional<Course> findById(final Long courseId) {
        return sqlCourseRepository.findByCourseIdValue(courseId).map(Course::restoreFromSnapshot);
    }

    @Override
    public List<Course> saveAll(final Iterable<Course> entities) {
        return sqlCourseRepository.saveAll(
                StreamSupport.stream(entities.spliterator(), false)
                             .map(Course::getSnapshot)
                             .collect(Collectors.toList()))
                             .stream()
                             .map(Course::restoreFromSnapshot)
                             .collect(Collectors.toList());
    }

    @Override
    public void deleteById(final Long id) {
        sqlCourseRepository.deleteByCourseIdValue(id);
    }

    @Override
    public Course save(final Course course) {
        return Course.restoreFromSnapshot(sqlCourseRepository.save(course.getSnapshot()));
    }
}
