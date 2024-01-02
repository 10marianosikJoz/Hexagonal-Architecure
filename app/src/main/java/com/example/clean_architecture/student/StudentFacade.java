package com.example.clean_architecture.student;

import com.example.clean_architecture.DomainEventPublisher;
import com.example.clean_architecture.course.Status;
import com.example.clean_architecture.course.vo.CourseSnapshot;
import com.example.clean_architecture.student.dto.CommandStudentDto;
import com.example.clean_architecture.student.dto.QueryStudentDto;
import com.example.clean_architecture.student.exception.BusinessStudentException;
import com.example.clean_architecture.student.vo.StudentEvent;
import com.example.clean_architecture.student.vo.StudentSnapshot;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


public class StudentFacade {

    static CommandStudentDto toStudentDto(Student student) {
        return CommandStudentDto.builder()
                                .withStudentId(student.getSnapshot().getStudentId())
                                .withFirstname(student.getSnapshot().getFirstName())
                                .withLastname(student.getSnapshot().getLastName())
                                .withEmail(student.getSnapshot().getEmail())
                                .withStatus(student.getSnapshot().getStatus())
                                .build();
    }

    static QueryStudentDto queryStudentDto(Student student) {
        return QueryStudentDto.builder()
                              .withStudentId(student.getSnapshot().getStudentId())
                              .withFirstname(student.getSnapshot().getFirstName())
                              .withLastname(student.getSnapshot().getLastName())
                              .withEmail(student.getSnapshot().getEmail())
                              .withStatus(student.getSnapshot().getStatus())
                              .build();
    }

    public static QueryStudentDto restoreFromSnapshot(StudentSnapshot snapshot) {
        return new QueryStudentDto(snapshot.getStudentId(),
                                   snapshot.getFirstName(),
                                   snapshot.getLastName(),
                                   snapshot.getEmail(),
                                   snapshot.getStatus()
        );
    }

    private final StudentRepository studentRepository;
    private final StudentFactory studentFactory;
    private final DomainEventPublisher publisher;

    StudentFacade(final StudentRepository studentRepository,
                  final StudentFactory studentFactory,
                  final DomainEventPublisher publisher) {

        this.studentRepository = studentRepository;
        this.studentFactory = studentFactory;
        this.publisher = publisher;
    }

    public List<QueryStudentDto> getStudentByEmails(List<String> emails) {
        return studentRepository.findAllByEmailIn(emails).stream()
                                                         .map(StudentFacade::queryStudentDto)
                                                         .collect(Collectors.toList());
    }

    @Transactional
    public void deleteStudentById(Long studentId) {
        studentRepository.findById(studentId)
                         .ifPresent(student -> {
                            var courseSnapshots = student.getSnapshot().getCourses();

                            courseSnapshots.forEach(course -> course.getStudents()
                                            .removeIf(stud -> stud.getStudentId().getValue().equals(studentId)));

                            courseSnapshots.stream()
                                        .filter(item -> item.getStatus().equals(Status.FULL))
                                        .forEach(CourseSnapshot::changeToActiveStatus);

                    studentRepository.deleteById(studentId);
                    publisher.publish(new StudentEvent(student.getSnapshot().getStudentId().getValue(),
                                                       new StudentEvent.Data(null, null, null, courseSnapshots),
                                                       StudentEvent.State.DELETED));
                });
    }

    CommandStudentDto addNewStudent(CommandStudentDto student) {
        var persisted = studentRepository.save(studentFactory.from(student));
        publisher.publish(new StudentEvent(persisted.getSnapshot().getStudentId().getValue(),
                                           new StudentEvent.Data(persisted.getSnapshot().getFirstName(),
                                                                 persisted.getSnapshot().getLastName(),
                                                                 persisted.getSnapshot().getEmail(),
                                                                 persisted.getSnapshot().getCourses()),
                                           StudentEvent.State.CREATED));

        return toStudentDto(persisted);
    }

    CommandStudentDto updateStudent(Long studentId, CommandStudentDto commandStudentDto) {
        return studentRepository.findById(studentId)
                                .map(studentFromDB -> {
                                     studentFromDB.updateDetails(
                                     commandStudentDto.getFirstName(),
                                     commandStudentDto.getLastName(),
                                     commandStudentDto.getEmail(),
                                     commandStudentDto.getStatus());

                    var persisted = studentRepository.save(studentFromDB);
                    publisher.publish(new StudentEvent(studentFromDB.getSnapshot().getStudentId().getValue(),
                                                       new StudentEvent.Data(commandStudentDto.getFirstName(),
                                                                             commandStudentDto.getLastName(),
                                                                             commandStudentDto.getEmail(),
                                                                             commandStudentDto.getCourses()),
                                                       StudentEvent.State.UPDATED));

                    return toStudentDto(persisted);
                }).orElseThrow(() -> BusinessStudentException.notFound("Student with id: " + studentId + " not found"));
    }
}
