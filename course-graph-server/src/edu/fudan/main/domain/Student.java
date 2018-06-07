package edu.fudan.main.domain;

import org.neo4j.ogm.annotation.Relationship;

import java.util.List;


public class Student extends User {

    @Relationship(type = "STUDENT_OF")
    private List<Course> courseList;

    public Student(){
    }

    public Student(long id, String name, String password, String email) {
        super(id, name, password, email, UserType.STUDENT);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }




}
