package com.example.clean_architecture.student

import com.example.clean_architecture.course.Status
import com.example.clean_architecture.course.vo.CourseId
import com.example.clean_architecture.course.vo.CourseSnapshot
import com.example.clean_architecture.course.vo.Description
import com.example.clean_architecture.course.vo.EndDate
import com.example.clean_architecture.course.vo.Name
import com.example.clean_architecture.course.vo.ParticipantLimit
import com.example.clean_architecture.course.vo.ParticipantNumber
import com.example.clean_architecture.course.vo.StartDate
import com.example.clean_architecture.student.vo.Email
import com.example.clean_architecture.student.vo.Firstname
import com.example.clean_architecture.student.vo.Lastname
import com.example.clean_architecture.student.event.StudentEvent
import com.example.clean_architecture.student.vo.StudentId
import com.example.clean_architecture.student.vo.StudentSnapshot
import spock.lang.Specification

import java.time.LocalDateTime

class StudentTest extends Specification {

    def "should restore student domain object from snapshot"() {
        given:
        def initialStudentSnapshot = prepareSnapshotData()

        when:
        def restoredStudent = Student.restoreFromSnapshot(initialStudentSnapshot)

        then:
        restoredStudent.class == Student.class
    }

    def "should restore snapshot from student domain object"() {
        given:
        def student = prepareStudentData()

        when:
        def studentSnapshot = Student.restoreFromStudent(student)

        then:
        studentSnapshot.class == StudentSnapshot.class
    }

    def "should update student necessary data"() {
        given:
        def student = prepareStudentData();
        def firstName = new Firstname("John")
        def lastName = new Lastname("Murphy")
        def email = new Email("murphy@gmail.com")
        def status = StudentSnapshot.Status.ACTIVE

        when:
        def updatedStudent = student.updateDetails(firstName, lastName, email, status)

        then:
        updatedStudent.getState() == StudentEvent.State.UPDATED
    }

    private StudentSnapshot prepareSnapshotData() {
        def set = new HashSet<>()
        set.add(prepareCourseSnapshotData());

        return StudentSnapshot.builder()
                              .withStudentId(new StudentId(0L))
                              .withFirstname(new Firstname("John"))
                              .withLastname(new Lastname("Murphy"))
                              .withEmail(new Email("murphy@gmail.com"))
                              .withCourses(set)
                              .withStatus(StudentSnapshot.Status.ACTIVE)
                              .build()
    }

    private Student prepareStudentData() {
        return Student.builder()
                      .withStudentId(new StudentId(0L))
                      .withFirstname(new Firstname("John"))
                      .withLastname(new Lastname("Murphy"))
                      .withEmail(new Email("murphy@gmail.com"))
                      .withCourses(new HashSet<>())
                      .withStatus(StudentSnapshot.Status.ACTIVE)
                      .build()

    }

    private CourseSnapshot prepareCourseSnapshotData() {
        return CourseSnapshot.builder()
                             .withCourseId(new CourseId(1L))
                             .withName(new Name("Groovy course"))
                             .withDescription(new Description("Complex course for programmers"))
                             .withStartDate(new StartDate(LocalDateTime.now()))
                             .withEndDate(new EndDate(LocalDateTime.now()))
                             .withParticipantLimit(new ParticipantLimit(200))
                             .withParticipantNumber(new ParticipantNumber(200))
                             .withStatus(Status.FULL)
                             .build();
    }
}