package edu.fudan.main.dto.response;

import edu.fudan.main.domain.Teacher;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.Date;

@QueryResult
public class CourseMetaResp {
    String name;
    Long id;
    Teacher teacher;
    Date createdTime;
    Date modifiedTime;
    int studentNum;

    public CourseMetaResp(String name, Long id, Teacher teacher, Date createdTime, Date modifiedTime, int studentNum) {
        this.name = name;
        this.id = id;
        this.teacher = teacher;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
        this.studentNum = studentNum;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public int getStudentNum() {
        return studentNum;
    }

}
