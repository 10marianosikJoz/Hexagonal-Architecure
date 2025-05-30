package com.example.clean_architecture.teacher

import com.example.clean_architecture.teacher.vo.Degree
import com.example.clean_architecture.teacher.vo.Firstname
import com.example.clean_architecture.teacher.vo.Lastname
import com.example.clean_architecture.teacher.vo.TeacherId
import com.example.clean_architecture.teacher.vo.TeacherSnapshot
import spock.lang.Specification

class TeacherTest extends Specification {

    def "should restore teacher domain object from snapshot"() {
        given:
        def initialTeacherSnapshot = prepareSnapshotData()

        when:
        def restoredTeacher = Teacher.restoreFromSnapshot(initialTeacherSnapshot)

        then:
        restoredTeacher.class == Teacher.class
    }

    def "should update necessary teacher data"() {
        given:
        def initialTeacher = Teacher.restoreFromSnapshot(prepareSnapshotData())
        def firstName = new Firstname("Johny")
        def lastName = new Lastname("Eilish")
        def degree = new Degree("Engineer")

        when:
        initialTeacher.updateTeacherData(firstName, lastName, degree)

        then:
        initialTeacher.getSnapshot().firstName.value() == "Johny"
    }

    private TeacherSnapshot prepareSnapshotData() {
        return TeacherSnapshot.builder()
                              .withTeacherId(new TeacherId(1L))
                              .withFirstname(new Firstname("Murphy"))
                              .withLastname(new Lastname("Greenwood"))
                              .withDegree(new Degree("Master degree"))
                              .build();
    }
}