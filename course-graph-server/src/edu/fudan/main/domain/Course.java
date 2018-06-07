package edu.fudan.main.domain;

import org.neo4j.ogm.annotation.*;

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

    @Relationship(type = "GRAPH_OF", direction = Relationship.INCOMING)
    List<CourseGraph> courseGraphList;

    @Relationship(type = "STUDENT_OF", direction = Relationship.INCOMING)
    Set<Student> students;

    @Relationship(type = "TEACHER_OF", direction = Relationship.INCOMING)
    Teacher teacher;

}
