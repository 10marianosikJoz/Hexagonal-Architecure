package com.example.clean_architecture.course

import com.example.clean_architecture.course.exception.BusinessCourseException
import com.example.clean_architecture.course.event.CourseEvent
import com.example.clean_architecture.course.vo.CourseId
import com.example.clean_architecture.course.vo.CourseSnapshot
import com.example.clean_architecture.course.vo.Description
import com.example.clean_architecture.course.vo.EndDate
import com.example.clean_architecture.course.vo.Name
import com.example.clean_architecture.course.vo.ParticipantLimit
import com.example.clean_architecture.course.vo.ParticipantNumber
import com.example.clean_architecture.course.vo.StartDate
import spock.lang.Specification

import java.time.LocalDateTime

class CourseTest extends Specification {

    def "should update course necessary data"() {
        given:
        def initialCourse = prepareCourseData()

        when:
        def updatedCourse = initialCourse.updateCourseInformation()

        then:
        updatedCourse.getState() == CourseEvent.State.UPDATED
    }

    def "should restore course domain object from snapshot"() {
        given:
        def initialCourseSnapshot = prepareCourseSnapshotData()

        when:
        def restoredCourse = Course.restoreFromSnapshot(initialCourseSnapshot)

        then:
        restoredCourse.class == Course.class
    }

    def "should restore snapshot object from course domain object"() {
        given:
        def initialCourse = prepareCourseData()

        when:
        def restoredCourseSnapshot = Course.restoreFromCourse(initialCourse)

        then:
        restoredCourseSnapshot.class == CourseSnapshot.class
    }

    def "should not let enroll course when course status is full"() {
        given:
        def initialCourseSnapshot = prepareCourseSnapshotData()
        def restoredCourse = Course.restoreFromSnapshot(initialCourseSnapshot)

        when:
        restoredCourse.validateCourseStatus()

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

    private CourseSnapshot prepareCourseSnapshotData() {
        return CourseSnapshot.builder()
                             .withCourseId(new CourseId(0L))
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