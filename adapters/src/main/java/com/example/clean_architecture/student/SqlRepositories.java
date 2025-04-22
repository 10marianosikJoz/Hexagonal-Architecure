package com.example.clean_architecture.student;

import com.example.clean_architecture.student.vo.StudentId;
import com.example.clean_architecture.student.vo.StudentSnapshot;
import org.springframework.data.repository.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

interface SqlStudentQueryRepository extends StudentQueryRepository, Repository<StudentSnapshot, StudentId> {}

interface SqlStudentRepository extends Repository<StudentSnapshot, StudentId> {


    Optional<StudentSnapshot> findByStudentIdValue(Long studentId);

    void deleteByStudentIdValue(Long id);

    StudentSnapshot save(StudentSnapshot student);

    List<StudentSnapshot> findAll();

}

@org.springframework.stereotype.Repository
class StudentJpaRepository implements StudentRepository {

    private final SqlStudentRepository studentRepository;

    StudentJpaRepository(SqlStudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Optional<Student> findById(final Long studentId) {
        return studentRepository.findByStudentIdValue(studentId)
                                .map(Student::restoreFromSnapshot);
    }

    @Override
    public void deleteById(final Long id) {
        studentRepository.deleteByStudentIdValue(id);
    }

    @Override
    public Student save(final Student student) {
        return Student.restoreFromSnapshot(studentRepository.save(student.getSnapshot()));
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll().stream()
                                          .map(Student::restoreFromSnapshot)
                                          .collect(Collectors.toList());
    }

    @Override
    public List<Student> findAllByEmailIn(final List<String> emails) {
        Set<String> email = new HashSet<>(emails);
        return findAll().stream()
                        .map(Student::restoreFromStudent)
                        .filter(it -> email.contains(it.getEmail().value()))
                        .map(Student::restoreFromSnapshot)
                        .collect(Collectors.toList());
    }
}