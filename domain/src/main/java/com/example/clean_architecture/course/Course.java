package com.example.clean_architecture.course;

import com.example.clean_architecture.course.exception.BusinessCourseException;
import com.example.clean_architecture.course.vo.CourseCreator;
import com.example.clean_architecture.course.vo.CourseEvent;
import com.example.clean_architecture.course.vo.CourseId;
import com.example.clean_architecture.course.vo.CourseSnapshot;
import com.example.clean_architecture.course.vo.Description;
import com.example.clean_architecture.course.vo.EndDate;
import com.example.clean_architecture.course.vo.Name;
import com.example.clean_architecture.course.vo.ParticipantLimit;
import com.example.clean_architecture.course.vo.ParticipantNumber;
import com.example.clean_architecture.course.vo.StartDate;
import com.example.clean_architecture.student.vo.StudentSnapshot;
import com.example.clean_architecture.teacher.vo.TeacherSourceId;

import java.util.HashSet;
import java.util.Set;

   class Course {

      static Course restoreFromSnapshot(CourseSnapshot snapshot) {
        var course =  Course.builder()
                            .withCourseId(snapshot.getCourseId())
                            .withName(snapshot.getName())
                            .withDescription(snapshot.getDescription())
                            .withStartDate(snapshot.getStartDate())
                            .withEndDate(snapshot.getEndDate())
                            .withParticipantLimit(snapshot.getParticipantLimit())
                            .withParticipantNumber(snapshot.getParticipantNumber())
                            .withStatus(snapshot.getStatus())
                            .withTeacherSourceId(snapshot.getTeacherSourceId())
                            .build();

        course.getStudents().addAll(snapshot.getStudents());
        return course;
    }

      static CourseSnapshot restoreFromCourse(Course course) {
          return CourseSnapshot.builder()
                               .withCourseId(course.courseId)
                               .withName(course.name)
                               .withDescription(course.description)
                               .withStartDate(course.startDate)
                               .withEndDate(course.endDate)
                               .withParticipantLimit(course.participantLimit)
                               .withParticipantNumber(course.participantNumber)
                               .withStatus(course.status)
                               .withTeacherSourceId(course.teacherSourceId)
                               .withStudents(course.students)
                               .build();
    }

    static Course createFrom(final CourseCreator courseCreator) {
        return Course.builder()
                     .withName(courseCreator.name())
                     .withDescription(courseCreator.description())
                     .withStartDate(courseCreator.startDate())
                     .withEndDate(courseCreator.endDate())
                     .withParticipantLimit(courseCreator.participantLimit())
                     .withParticipantNumber(courseCreator.participantNumber())
                     .withStatus(courseCreator.status())
                     .build();
    }

    public static CourseBuilder builder() {
          return new CourseBuilder();
    }

       private final CourseId courseId;
       private final Name name;
       private final Description description;
       private final StartDate startDate;
       private final EndDate endDate;
       private final ParticipantLimit participantLimit;
       private final ParticipantNumber participantNumber;
       private Status status;
       private final TeacherSourceId teacherSourceId;
       private final Set<StudentSnapshot> students = new HashSet<>();

     Course(final CourseId courseId,
            final Name name,
            final Description description,
            final StartDate startDate,
            final EndDate endDate,
            final ParticipantLimit participantsLimit,
            final ParticipantNumber participantsNumber,
            final Status status,
            final TeacherSourceId teacherSourceId) {

        this.courseId = courseId;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.participantLimit = participantsLimit;
        this.participantNumber = participantsNumber;
        this.status = status;
        this.teacherSourceId = teacherSourceId;
    }

    CourseEvent updateCourseInformation() {
        return new CourseEvent(courseId.getValue(),
                               new CourseEvent.Data(name,
                                                    description,
                                                    startDate,
                                                    endDate),
                               CourseEvent.State.UPDATED);
    }

    CourseEvent changeStatus() {
        if (participantNumber.getValue().equals(participantLimit.getValue())) {
            this.status = Status.FULL;
        }

        return new CourseEvent(courseId.getValue(), null, CourseEvent.State.UPDATED);
      }

    Set<StudentSnapshot> getStudents() {
        return students;
    }

   void validateCourseStatus() {
         if (status.equals(Status.FULL)) {
             throw BusinessCourseException.limitExceeded("Course participants limit exceeded");
         }
     }

    CourseSnapshot getSnapshot() {
        return CourseSnapshot.builder()
                             .withCourseId(courseId)
                             .withName(name)
                             .withDescription(description)
                             .withStartDate(startDate)
                             .withEndDate(endDate)
                             .withParticipantLimit(participantLimit)
                             .withParticipantNumber(participantNumber)
                             .withStatus(status)
                             .withTeacherSourceId(teacherSourceId)
                             .withStudents(students)
                             .build();
    }

     void addStudent(StudentSnapshot student) {
         students.add(student);
         student.getCourses().add(Course.restoreFromCourse(this));
     }

     public static class CourseBuilder {

         private CourseId courseId;
         private Name name;
         private Description description;
         private StartDate startDate;
         private EndDate endDate;
         private ParticipantLimit participantLimit;
         private ParticipantNumber participantNumber;
         private Status status;
         private TeacherSourceId teacherSourceId;

         private CourseBuilder() {}

         public CourseBuilder withCourseId(CourseId courseId) {
             this.courseId = courseId;
             return this;
         }

         public CourseBuilder withName(Name name) {
             this.name = name;
             return this;
         }

         public CourseBuilder withDescription(Description description) {
             this.description = description;
             return this;
         }

         public CourseBuilder withStartDate(StartDate startDate) {
             this.startDate = startDate;
             return this;
         }

         public CourseBuilder withEndDate(EndDate endDate) {
             this.endDate = endDate;
             return this;
         }

         public CourseBuilder withParticipantLimit(ParticipantLimit participantLimit) {
             this.participantLimit = participantLimit;
             return this;
         }

         public CourseBuilder withParticipantNumber(ParticipantNumber participantNumber) {
             this.participantNumber = participantNumber;
             return this;
         }

         public CourseBuilder withStatus(Status status) {
             this.status = status;
             return this;
         }

         public CourseBuilder withTeacherSourceId(TeacherSourceId teacherSourceId) {
             this.teacherSourceId = teacherSourceId;
             return this;
         }

         Course build() {
             return new Course(courseId,
                               name,
                               description,
                               startDate,
                               endDate,
                               participantLimit,
                               participantNumber,
                               status,
                               teacherSourceId);
         }
     }
  }
