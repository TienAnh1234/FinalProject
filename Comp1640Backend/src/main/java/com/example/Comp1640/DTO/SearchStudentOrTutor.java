package com.example.Comp1640.DTO;

public class SearchStudentOrTutor {

    String username;
    Long districtId;
    Long gradeId;
    Long majorId;

    public SearchStudentOrTutor(String username, Long districtId, Long gradeId, Long majorId) {
        this.username = username;
        this.districtId = districtId;
        this.gradeId = gradeId;
        this.majorId = majorId;
    }

    public SearchStudentOrTutor() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    public Long getMajorId() {
        return majorId;
    }

    public void setMajorId(Long majorId) {
        this.majorId = majorId;
    }
}
