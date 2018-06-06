package edu.fudan.main.domain;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;
import java.util.Set;

@NodeEntity
public class Course {
    @Id
    private Long courseId;

    @Property
    private String name;

    @Property
    private String code;

    @Relationship(type = "GRAPH_OF", direction = Relationship.INCOMING)
    List<CourseGraph> courseGraphList;

    @Relationship(type = "STUDENT_OF", direction = Relationship.INCOMING)
    Set<Student> students;

    @Relationship(type = "TEACHER_OF", direction = Relationship.INCOMING)
    Teacher teacher;

}
