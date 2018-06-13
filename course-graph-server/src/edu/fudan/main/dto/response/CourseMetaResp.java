package edu.fudan.main.dto.response;

import edu.fudan.main.domain.Course;

import java.util.Date;

public class CourseMetaResp {

    private String name;

    private long id;

    private String teacherName;

    private long teacherId;

    private Date createdTime;

    private Date modifiedTime;

    private int studentNum;

    public CourseMetaResp(String name, long id, String teacherName, long teacherId , Date createdTime, Date modifiedTime, int studentNum) {
        this.name = name;
        this.id = id;
        this.teacherName = teacherName;
        this.teacherId = teacherId;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
        this.studentNum = studentNum;
    }

    public CourseMetaResp(Course course) {
        this.name = course.getName();
        this.id = course.getCourseId();
        this.teacherName = course.getTeacher().getName();
        this.teacherId = course.getTeacher().getId();
        this.createdTime = course.getCreatedTime();
        this.modifiedTime = course.getModifiedTime();
        this.studentNum = course.getStudents().size();
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public long getTeacherId(){
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
