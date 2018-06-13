package edu.fudan.main.domain;

import org.neo4j.ogm.annotation.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.*;

@NodeEntity
public class Course {
    @Id
    private Long courseId;

    @Property
    private String name;

    @Property
    @Index(unique = true)
    private String code;

    @Property
    @CreatedDate
    private Date createdTime;

    @Property
    @LastModifiedDate
    private Date modifiedTime;

    @Relationship(type = "GRAPH_OF", direction = Relationship.INCOMING)
    private List<Graph> graphList;

    @Relationship(type = "STUDENT_OF", direction = Relationship.INCOMING)
    private Set<Student> students;

    @Relationship(type = "TEACHER_OF", direction = Relationship.INCOMING)
    private Teacher teacher;

    public Course() {
    }

    public Course(String code, String name, Long courseId, Teacher teacher){
        this.courseId = courseId;
        this.name = name;
        this.code = code;
        this.teacher = teacher;
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

    public List<Graph> getGraphList() {
        return graphList;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void addAStudent(Student student) {
        if (students == null) {
            students = new HashSet<>();
        }

        students.add(student);
    }
}
