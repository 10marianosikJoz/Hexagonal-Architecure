package com.example.clean_architecture.student;

import com.example.clean_architecture.DomainEventPublisher;
import com.example.clean_architecture.course.Status;
import com.example.clean_architecture.course.vo.CourseSnapshot;
import com.example.clean_architecture.student.dto.CommandStudentDto;
import com.example.clean_architecture.student.dto.QueryStudentDto;
import com.example.clean_architecture.student.exception.BusinessStudentException;
import com.example.clean_architecture.student.event.StudentEvent;
import com.example.clean_architecture.student.vo.StudentSnapshot;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public class StudentFacade {

    private final StudentRepository studentRepository;
    private final StudentQueryRepository studentQueryRepository;
    private final StudentFactory studentFactory;
    private final DomainEventPublisher publisher;

    StudentFacade(final StudentRepository studentRepository,
                  final StudentQueryRepository studentQueryRepository,
                  final StudentFactory studentFactory,
                  final DomainEventPublisher publisher) {

        this.studentRepository = studentRepository;
        this.studentQueryRepository = studentQueryRepository;
        this.studentFactory = studentFactory;
        this.publisher = publisher;
    }

    @Transactional
    void deleteStudentById(Long studentId) {
        studentRepository.findById(studentId)
                         .ifPresent(student -> {
                            var courseSnapshots = student.getSnapshot().getCourses();

                            courseSnapshots.forEach(course -> course.getStudents()
                                            .removeIf(stud -> stud.getStudentId().value().equals(studentId)));

                            courseSnapshots.stream()
                                        .filter(item -> item.getStatus().equals(Status.FULL))
                                        .forEach(CourseSnapshot::changeToActiveStatus);

                    studentRepository.deleteById(studentId);
                    publisher.publish(new StudentEvent(student.getSnapshot().getStudentId().value(),
                                                       new StudentEvent.Data(student.getSnapshot().getFirstName(),
                                                                             student.getSnapshot().getLastName(),
                                                                             student.getSnapshot().getEmail(),
                                                                             courseSnapshots),
                                                       StudentEvent.State.DELETED));
                });
    }

    @Transactional
    public CommandStudentDto addNewStudent(CommandStudentDto student) {
        var persisted = studentRepository.save(studentFactory.from(student));
        publisher.publish(new StudentEvent(persisted.getSnapshot().getStudentId().value(),
                                           new StudentEvent.Data(persisted.getSnapshot().getFirstName(),
                                                                 persisted.getSnapshot().getLastName(),
                                                                 persisted.getSnapshot().getEmail(),
                                                                 persisted.getSnapshot().getCourses()),
                                           StudentEvent.State.CREATED));

        return toStudentDto(persisted);
    }

    @Transactional
    CommandStudentDto updateStudent(Long studentId, CommandStudentDto commandStudentDto) {
        return studentRepository.findById(studentId)
                                .map(studentFromDB -> {
                                     studentFromDB.updateDetails(
                                     commandStudentDto.firstName(),
                                     commandStudentDto.lastName(),
                                     commandStudentDto.email(),
                                     commandStudentDto.status());

                    var persisted = studentRepository.save(studentFromDB);
                    publisher.publish(new StudentEvent(studentFromDB.getSnapshot().getStudentId().value(),
                                                       new StudentEvent.Data(commandStudentDto.firstName(),
                                                                             commandStudentDto.lastName(),
                                                                             commandStudentDto.email(),
                                                                             commandStudentDto.courses()),
                                                       StudentEvent.State.UPDATED));

                    return toStudentDto(persisted);
                }).orElseThrow(() -> BusinessStudentException.notFound("Student with id: " + studentId + " not found"));
    }

    @Transactional(readOnly = true)
    public List<QueryStudentDto> getStudentByEmails(List<String> emails) {
        return studentRepository.findAllByEmailIn(emails).stream()
                .map(this::queryStudentDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public QueryStudentDto findStudentDtoById(Long studentId) {
        return studentQueryRepository.findDtoByStudentIdValue(studentId)
                .orElseThrow(() -> BusinessStudentException.notFound("Student with id: " + studentId + " not found"));
    }

    public QueryStudentDto restoreFromSnapshot(StudentSnapshot snapshot) {
        return new QueryStudentDto(snapshot.getStudentId(),
                                   snapshot.getFirstName(),
                                   snapshot.getLastName(),
                                   snapshot.getEmail(),
                                   snapshot.getCourses(),
                                   snapshot.getStatus()
        );
    }

    CommandStudentDto toStudentDto(Student student) {
        return CommandStudentDto.builder()
                .withStudentId(student.getSnapshot().getStudentId())
                .withFirstname(student.getSnapshot().getFirstName())
                .withLastname(student.getSnapshot().getLastName())
                .withEmail(student.getSnapshot().getEmail())
                .withStatus(student.getSnapshot().getStatus())
                .withCourses(student.getSnapshot().getCourses())
                .build();
    }

    private QueryStudentDto queryStudentDto(Student student) {
        return QueryStudentDto.builder()
                .withStudentId(student.getSnapshot().getStudentId())
                .withFirstname(student.getSnapshot().getFirstName())
                .withLastname(student.getSnapshot().getLastName())
                .withEmail(student.getSnapshot().getEmail())
                .withStatus(student.getSnapshot().getStatus())
                .withCourses(student.getSnapshot().getCourses())
                .build();
    }
}