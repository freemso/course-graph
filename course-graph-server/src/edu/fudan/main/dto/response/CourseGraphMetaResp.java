package edu.fudan.main.dto.response;

public class CourseGraphMetaResp {
    long courseGraphId;
    long courseId;
    String courseGraphName;

    public CourseGraphMetaResp(long courseGraphId, long courseId, String courseGraphName) {
        this.courseGraphId = courseGraphId;
        this.courseId = courseId;
        this.courseGraphName = courseGraphName;
    }

    public long getCourseGraphId() {
        return courseGraphId;
    }

    public long getCourseId() {
        return courseId;
    }

    public String getCourseGraphName() {
        return courseGraphName;
    }
}
