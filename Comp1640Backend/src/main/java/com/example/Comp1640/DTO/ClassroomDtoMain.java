package com.example.Comp1640.DTO;

import java.util.ArrayList;
import java.util.List;

public class ClassroomDtoMain {

    private Long id;
    private Long tutorId;
    private Long studentId;

    public ClassroomDtoMain() {}

    public ClassroomDtoMain(Long tutorId, Long studentId) {
        this.tutorId = tutorId;
        this.studentId = studentId;
    }

    public ClassroomDtoMain(Long id, Long tutorId, Long studentId) {
        this.id = id;
        this.tutorId = tutorId;
        this.studentId = studentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTutorId() {
        return tutorId;
    }

    public void setTutorId(Long tutorId) {
        this.tutorId = tutorId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
