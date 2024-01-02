package com.example.clean_architecture.course;

import com.example.clean_architecture.course.vo.CourseId;
import com.example.clean_architecture.course.vo.CourseSnapshot;
import com.example.clean_architecture.course.vo.Description;
import com.example.clean_architecture.course.vo.EndDate;
import com.example.clean_architecture.course.vo.Name;
import com.example.clean_architecture.course.vo.ParticipantLimit;
import com.example.clean_architecture.course.vo.ParticipantNumber;
import com.example.clean_architecture.course.vo.StartDate;
import com.example.clean_architecture.teacher.vo.TeacherSourceId;

import java.time.LocalDateTime;

class CourseInitializer {

    private final CourseRepository courseRepository;
    private final CourseQueryRepository courseQueryRepository;

    CourseInitializer(final CourseRepository courseRepository,
                      final CourseQueryRepository courseQueryRepository) {

        this.courseRepository = courseRepository;
        this.courseQueryRepository = courseQueryRepository;
    }

    void init() {
        if(courseQueryRepository.count() == 0) {
                 courseRepository.save(Course.restoreFromSnapshot(CourseSnapshot.builder()
                                                                                .withCourseId(new CourseId(0L))
                                                                                .withName(new Name("Example course"))
                                                                                .withDescription(new Description("Course for beginners"))
                                                                                .withStartDate(new StartDate(LocalDateTime.now()))
                                                                                .withEndDate(new EndDate(LocalDateTime.now()))
                                                                                .withParticipantLimit(new ParticipantLimit(100L))
                                                                                .withParticipantNumber(new ParticipantNumber(0L))
                                                                                .withStatus(Status.ACTIVE)
                                                                                .withTeacherSourceId(new TeacherSourceId("1"))
                                                                                .build()));
        }
    }
}
