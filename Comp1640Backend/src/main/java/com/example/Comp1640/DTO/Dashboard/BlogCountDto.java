package com.example.Comp1640.DTO.Dashboard;

public class BlogCountDto {
    private Integer month;
    private Long count;

    public BlogCountDto() {}

    public BlogCountDto(Integer month, Long count) {
        this.month = month;
        this.count = count;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "BlogCountDto{" +
                "month=" + month +
                ", count=" + count +
                '}';
    }
}
