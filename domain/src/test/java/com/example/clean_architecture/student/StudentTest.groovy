package com.example.clean_architecture.student

import com.example.clean_architecture.course.vo.CourseSnapshot
import com.example.clean_architecture.student.vo.Email
import com.example.clean_architecture.student.vo.Firstname
import com.example.clean_architecture.student.vo.Lastname
import com.example.clean_architecture.student.vo.StudentEvent
import com.example.clean_architecture.student.vo.StudentId
import com.example.clean_architecture.student.vo.StudentSnapshot
import spock.lang.Specification

class StudentTest extends Specification {

    def "should restore student object from snapshot"() {
        given:
        def studentSnapshot = prepareSnapshotData()

        when:
        def student = Student.restoreFromSnapshot(studentSnapshot)

        then:
        student.class == Student.class
    }

    def "should restore snapshot from domain object"() {
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
        def result = student.updateDetails(firstName, lastName, email, status)

        then:
        result.getState() == StudentEvent.State.UPDATED
    }

    private StudentSnapshot prepareSnapshotData() {
        return StudentSnapshot.builder()
                              .withStudentId(new StudentId(0L))
                              .withFirstname(new Firstname("John"))
                              .withLastname(new Lastname("Murphy"))
                              .withEmail(new Email("murphy@gmail.com"))
                              .withCourses([new CourseSnapshot()].toSet())
                              .withStatus(StudentSnapshot.Status.ACTIVE)
                              .build()
    }

    private Student prepareStudentData() {
        return Student.builder()
                      .withStudentId(new StudentId(0L))
                      .withFirstname(new Firstname("John"))
                      .withLastname(new Lastname("Murphy"))
                      .withEmail(new Email("murphy@gmail.com"))
                      .withStatus(StudentSnapshot.Status.ACTIVE)
                      .build()

    }
}
