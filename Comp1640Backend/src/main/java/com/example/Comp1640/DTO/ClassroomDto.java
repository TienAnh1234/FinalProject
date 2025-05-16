package com.example.Comp1640.DTO;

import java.util.ArrayList;
import java.util.List;

public class ClassroomDto {

    private Long id;
    private Long tutorId;
    private List<Long> studentsId = new ArrayList<>();

    public ClassroomDto(Long id, Long tutorId, List<Long> studentsId) {
        this.id = id;
        this.tutorId = tutorId;
        this.studentsId = studentsId;
    }

    public ClassroomDto( Long tutorId, List<Long> studentsId) {
        this.tutorId = tutorId;
        this.studentsId = studentsId;
    }

    public ClassroomDto() {
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

    public List<Long> getStudentsId() {
        return studentsId;
    }

    public void setStudentsId(List<Long> studentsId) {
        this.studentsId = studentsId;
    }
}
