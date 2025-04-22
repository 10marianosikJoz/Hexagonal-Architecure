package com.example.clean_architecture.teacher;

import com.example.clean_architecture.teacher.vo.TeacherId;
import com.example.clean_architecture.teacher.vo.TeacherSnapshot;
import org.springframework.data.repository.Repository;

import java.util.Optional;

interface SqlTeacherQueryRepository extends TeacherQueryRepository, Repository<TeacherSnapshot, TeacherId> {}

interface SqlTeacherRepository extends Repository<TeacherSnapshot, TeacherId> {

    Optional<TeacherSnapshot> findByTeacherId(Long teacherId);

    void deleteByTeacherId(Long teacherId);

    TeacherSnapshot save(TeacherSnapshot entity);
}

@org.springframework.stereotype.Repository
class TeacherJpaRepository implements TeacherRepository {
    private final SqlTeacherRepository sqlTeacherRepository;

    TeacherJpaRepository(final SqlTeacherRepository sqlTeacherRepository) {
        this.sqlTeacherRepository = sqlTeacherRepository;
    }

    @Override
    public Optional<Teacher> findByTeacherId(final Long teacherId) {
        return sqlTeacherRepository.findByTeacherId(teacherId)
                                   .map(Teacher::restoreFromSnapshot);
    }

    @Override
    public void deleteByTeacherId(final Long teacherId) {
        sqlTeacherRepository.deleteByTeacherId(teacherId);
    }

    @Override
    public Teacher save(final Teacher teacher) {
        return Teacher.restoreFromSnapshot(sqlTeacherRepository.save(teacher.getSnapshot()));
    }
}

