package edu.fudan.domain;

import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {

    @Relationship(type = "STUDENT_OF")
    private List<Course> courseList;

    public Student() {
    }

    public Student(long id, String name, String password, String email) {
        super(id, name, password, email, UserType.STUDENT);
        courseList = new ArrayList<>();
    }

    public void addCourse(Course course){
        this.courseList.add(course);
    }

    public List<Course> getCourseList() {
        return courseList == null ? new ArrayList<>() : courseList;
    }
}
