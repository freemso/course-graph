package edu.fudan.main.domain;

import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
public class CourseGraphMeta {
    long courseGraphId;
    long courseId;
    String courseGraphName;
//    private String description;
//    private String createdTime;
//    private String modifiedTime;

    public CourseGraphMeta(long courseGraphId, long courseId, String courseGraphName) {
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
