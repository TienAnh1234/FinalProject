package com.example.Comp1640.DTO;

import com.example.Comp1640.Entity.Grade;
import com.example.Comp1640.Entity.Major;
import jakarta.persistence.*;

public class MajorGradeDto {

    private Long id;

    private Long majorId;

    private Long gradeId;

    public MajorGradeDto(Long id, Long majorId, Long gradeId) {
        this.id = id;
        this.majorId = majorId;
        this.gradeId = gradeId;
    }

    public MajorGradeDto(Long majorId, Long gradeId) {
        this.majorId = majorId;
        this.gradeId = gradeId;
    }

    public MajorGradeDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMajorId() {
        return majorId;
    }

    public void setMajorId(Long majorId) {
        this.majorId = majorId;
    }

    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }
}
