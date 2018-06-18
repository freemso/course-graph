package edu.fudan.main.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.fudan.main.domain.Course;

public class CoursePublicResp {

    private String name;

    private long id;

    @JsonProperty("teacher_name")
    private String teacherName;

    @JsonProperty("teacher_id")
    private long teacherId;

    public CoursePublicResp() {
    }

    public CoursePublicResp(Course course) {
        this.name = course.getName();
        this.id = course.getCourseId();
        this.teacherId = course.getTeacher().getUserId();
        this.teacherName = course.getTeacher().getName();
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public long getTeacherId() {
        return teacherId;
    }
}
