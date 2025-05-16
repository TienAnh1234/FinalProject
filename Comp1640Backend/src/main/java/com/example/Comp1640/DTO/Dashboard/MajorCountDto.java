package com.example.Comp1640.DTO.Dashboard;

public class MajorCountDto {
    private String major;
    private Long count;

    public MajorCountDto(String major, Long count) {
        this.major = major;
        this.count = count;
    }

    public MajorCountDto() {}

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "MajorCountDto{" +
                "major='" + major + '\'' +
                ", count=" + count +
                '}';
    }
}
