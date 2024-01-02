package com.example.clean_architecture.teacher

import com.example.clean_architecture.teacher.vo.Degree
import com.example.clean_architecture.teacher.vo.Firstname
import com.example.clean_architecture.teacher.vo.Lastname
import com.example.clean_architecture.teacher.vo.TeacherId
import com.example.clean_architecture.teacher.vo.TeacherSnapshot
import spock.lang.Specification

class TeacherTest extends Specification {

    def "should restore teacher object from snapshot"() {
        given:
        def teacherSnapshot = prepareSnapshotData()

        when:
        def teacher = Teacher.restoreFromSnapshot(teacherSnapshot)

        then:
        teacher.class == Teacher.class
    }

    def "should update necessary teacher data"() {
        given:
        def teacher = Teacher.restoreFromSnapshot(prepareSnapshotData())
        def firstName = new Firstname("Johny")
        def lastName = new Lastname("Eilish")
        def degree = new Degree("Engineer")

        when:
        teacher.updateTeacherData(firstName, lastName, degree)

        then:
        teacher.getSnapshot().firstName.getValue() == "Johny"
    }

    private TeacherSnapshot prepareSnapshotData() {
        return TeacherSnapshot.builder()
                              .withFirstname(new Firstname("Murphy"))
                              .withLastname(new Lastname("Greenwood"))
                              .withDegree(new Degree("Master degree"))
                              .withTeacherId(new TeacherId(0L))
                              .build();
    }
}
