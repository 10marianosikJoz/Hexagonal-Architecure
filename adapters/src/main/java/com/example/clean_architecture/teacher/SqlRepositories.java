package com.example.clean_architecture.teacher;

import com.example.clean_architecture.teacher.vo.TeacherId;
import com.example.clean_architecture.teacher.vo.TeacherSnapshot;
import org.springframework.data.repository.Repository;

import java.util.Optional;

interface SqlTeacherQueryRepository extends TeacherQueryRepository, Repository<TeacherSnapshot, TeacherId> {}

interface SqlTeacherRepository extends Repository<TeacherSnapshot, TeacherId> {

    Optional<TeacherSnapshot> findByTeacherIdValue(Long teacherId);

    void deleteByTeacherIdValue(Long teacherId);

    TeacherSnapshot save(TeacherSnapshot entity);
}

@org.springframework.stereotype.Repository
class TeacherRepositoryImpl implements TeacherRepository {
    private final SqlTeacherRepository sqlTeacherRepository;

    TeacherRepositoryImpl(final SqlTeacherRepository sqlTeacherRepository) {
        this.sqlTeacherRepository = sqlTeacherRepository;
    }

    @Override
    public Optional<Teacher> findByTeacherIdValue(final Long teacherId) {
        return sqlTeacherRepository.findByTeacherIdValue(teacherId)
                                   .map(Teacher::restoreFromSnapshot);
    }

    @Override
    public void deleteByTeacherIdValue(final Long teacherId) {
        sqlTeacherRepository.deleteByTeacherIdValue(teacherId);
    }

    @Override
    public Teacher save(final Teacher teacher) {
        return Teacher.restoreFromSnapshot(sqlTeacherRepository.save(teacher.getSnapshot()));
    }
}

