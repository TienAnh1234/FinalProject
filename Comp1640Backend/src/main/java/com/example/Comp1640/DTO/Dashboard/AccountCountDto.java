package com.example.Comp1640.DTO.Dashboard;

public class AccountCountDto {
    private Long student;
    private Long tutor;

    public AccountCountDto() {}

    public AccountCountDto(Long student, Long tutor) {
        this.student = student;
        this.tutor = tutor;
    }

    public Long getStudent() {
        return student;
    }

    public void setStudent(Long student) {
        this.student = student;
    }

    public Long getTutor() {
        return tutor;
    }

    public void setTutor(Long tutor) {
        this.tutor = tutor;
    }

    @Override
    public String toString() {
        return "AccountCountDto{" +
                "student=" + student +
                ", tutor=" + tutor +
                '}';
    }
}
