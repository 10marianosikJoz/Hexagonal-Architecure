package com.example.clean_architecture.course

import com.example.clean_architecture.course.exception.BusinessCourseException
import com.example.clean_architecture.course.vo.CourseEvent
import com.example.clean_architecture.course.vo.CourseId
import com.example.clean_architecture.course.vo.CourseSnapshot
import com.example.clean_architecture.course.vo.Description
import com.example.clean_architecture.course.vo.EndDate
import com.example.clean_architecture.course.vo.Name
import com.example.clean_architecture.course.vo.ParticipantLimit
import com.example.clean_architecture.course.vo.ParticipantNumber
import com.example.clean_architecture.course.vo.StartDate
import com.example.clean_architecture.student.vo.StudentSnapshot
import spock.lang.Specification

import java.time.LocalDateTime

class CourseTest extends Specification {

    def "should update course necessary data"() {
        given:
        def course = prepareCourseData()

        when:
        def result = course.updateCourseInformation()

        then:
        result.getState() == CourseEvent.State.UPDATED
    }

    def "should restore course object from snapshot"() {
        given:
        def courseSnapshot = prepareSnapshotData()

        when:
        def course = Course.restoreFromSnapshot(courseSnapshot)

        then:
        course.class == Course.class
    }

    def "should restore snapshot object from domain object"() {
        given:
        def course = prepareCourseData()

        when:
        def courseSnapshot = Course.restoreFromCourse(course)

        then:
        courseSnapshot.class == CourseSnapshot.class
    }

    def "should NOT let enroll course when course status is full"() {
        given:
        def courseSnapshot = prepareSnapshotData()
        def restored = Course.restoreFromSnapshot(courseSnapshot)

        when:
        restored.validateCourseStatus()

        then:
        def exception = thrown BusinessCourseException
        exception.message.contains 'Course participants limit exceeded'
    }

    private Course prepareCourseData() {
        return Course.builder()
                     .withCourseId(new CourseId(0L))
                     .withName(new Name("Groovy course"))
                     .withDescription(new Description("Complex course for programmers"))
                     .withStartDate(new StartDate(LocalDateTime.now()))
                     .withEndDate(new EndDate(LocalDateTime.now()))
                     .withParticipantLimit(new ParticipantLimit(100))
                     .withParticipantNumber(new ParticipantNumber(100))
                     .withStatus(Status.FULL)
                     .build()

    }

    private CourseSnapshot prepareSnapshotData() {
        return CourseSnapshot.builder()
                             .withCourseId(new CourseId(0L))
                             .withName(new Name("Groovy course"))
                             .withDescription(new Description("Complex course for programmers"))
                             .withStartDate(new StartDate(LocalDateTime.now()))
                             .withEndDate(new EndDate(LocalDateTime.now()))
                             .withParticipantLimit(new ParticipantLimit(200))
                             .withParticipantNumber(new ParticipantNumber(200))
                             .withStatus(Status.FULL)
                             .withStudents([new StudentSnapshot()].toSet())
                             .build();
    }
}
