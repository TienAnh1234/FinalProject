package com.example.Comp1640.DTO;

import com.example.Comp1640.Entity.MajorGrade;
import com.example.Comp1640.Entity.Tutor;
import jakarta.persistence.*;

public class MajorgradeTutorDto {

    private Long id;

    private Long majorGradeId;

    private Long tutorId;

    public MajorgradeTutorDto(Long id, Long majorGradeId, Long tutorId) {
        this.id = id;
        this.majorGradeId = majorGradeId;
        this.tutorId = tutorId;
    }

    public MajorgradeTutorDto(Long majorGradeId, Long tutorId) {
        this.majorGradeId = majorGradeId;
        this.tutorId = tutorId;
    }

    public MajorgradeTutorDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMajorGradeId() {
        return majorGradeId;
    }

    public void setMajorGradeId(Long majorGradeId) {
        this.majorGradeId = majorGradeId;
    }

    public Long getTutorId() {
        return tutorId;
    }

    public void setTutorId(Long tutorId) {
        this.tutorId = tutorId;
    }
}
