package edu.fudan.main.domain;

import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

public class Teacher extends User {

    @Relationship(type = "TEACHER_OF", direction = Relationship.OUTGOING)
    private List<Course> courseList;

    public Teacher(){
    }

    public Teacher(long id, String name, String password, String email) {
        super(id, name, password, email,UserType.TEACHER);
    }

    @Override
    public boolean equals(Object o){
        return super.equals(o);
    }
}
