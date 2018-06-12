package edu.fudan.main.domain;

import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.DateString;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

@NodeEntity
public class Course {
    @Id
    private Long courseId;

    @Property
    private String name;

    @Property@Index(unique = true)
    private String code;

    @Property
    @DateString("yyyy-MM-dd’T’HH:mm:ss")
    private Date createdTime;

    @Property
    private Date modifiedTime;



    @Relationship(type = "GRAPH_OF", direction = Relationship.INCOMING)
    List<CourseGraph> courseGraphList;

    @Relationship(type = "STUDENT_OF", direction = Relationship.INCOMING)
    Set<Student> students;

    @Relationship(type = "TEACHER_OF", direction = Relationship.INCOMING)
    Teacher teacher;


    public Course(String code, String name, Long courseId){
        this.courseId = courseId;
        this.name = name;
        this.code = code;
        this.createdTime = Calendar.getInstance().getTime();
        this.modifiedTime = this.createdTime;
    }

    public void setName(String name){
        this.name = name;
        this.modifiedTime = Calendar.getInstance().getTime();
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public List<CourseGraph> getCourseGraphList() {
        return courseGraphList;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public Teacher getTeacher() {
        return teacher;
    }
}
