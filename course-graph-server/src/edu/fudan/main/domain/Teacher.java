package edu.fudan.main.domain;

import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends User {

    @Relationship(type = "TEACHER_OF")
    private List<Course> courseList;

    public Teacher() {
    }

    public Teacher(long id, String name, String password, String email) {
        super(id, name, password, email,UserType.TEACHER);
        this.courseList = new ArrayList<>();
    }

    public void addCourse(Course course){
        this.courseList.add(course);
    }


    public List<Course> getCourseList() {
        return courseList;
    }
}
