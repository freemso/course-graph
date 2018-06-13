package edu.fudan.main.dto.response;

import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.Date;

@QueryResult
public class CourseMetaResp {
    private String name;
    private Long id;
    private String teacherName;
    private Long teacherId;
    private Date createdTime;
    private Date modifiedTime;
    private int studentNum;

    public CourseMetaResp(String name, Long id, String teacherName, Long teacherId , Date createdTime, Date modifiedTime, int studentNum) {
        this.name = name;
        this.id = id;
        this.teacherName = teacherName;
        this.teacherId = teacherId;
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

    public String getTeacherName() {
        return teacherName;
    }

    public Long getTeacherId(){
        return teacherId;
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
