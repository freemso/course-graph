package edu.fudan.main.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.fudan.main.domain.Course;

public class CourseMetaResp {

    private String name;

    private long id;

    private String code;

    @JsonProperty("teacher_name")
    private String teacherName;

    @JsonProperty("teacher_id")
    private long teacherId;

//    @JsonProperty("created_time")
//    private Date createdTime;
//
//    @JsonProperty("modified_time")
//    private Date modifiedTime;

    @JsonProperty("student_num")
    private int studentNum;

    public CourseMetaResp() {
    }

    public CourseMetaResp(Course course) {
        this.name = course.getName();
        this.id = course.getCourseId();
        this.code = course.getCode();
        this.teacherName = course.getTeacher().getName();
        this.teacherId = course.getTeacher().getId();
//        this.createdTime = course.getCreatedTime();
//        this.modifiedTime = course.getModifiedTime();
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

//    public Date getCreatedTime() {
//        return createdTime;
//    }
//
//    public Date getModifiedTime() {
//        return modifiedTime;
//    }

    public int getStudentNum() {
        return studentNum;
    }

    public String getCode() {
        return code;
    }
}
