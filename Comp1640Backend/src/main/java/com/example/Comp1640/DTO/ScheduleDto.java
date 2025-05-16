package com.example.Comp1640.DTO;

import com.example.Comp1640.Entity.Student;
import com.example.Comp1640.Entity.Tutor;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class ScheduleDto {

    private Long id;

    private StudentDto studentDto;

    private TutorDto tutorDto;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String dayOfWeek; // Thứ (VD: "Thứ Hai")

    private String weekOfYear; // Số tuần trong năm (VD: 14)

    private String status;

    private String scheduleFormat;

    private String address;

    public ScheduleDto() {}

    public ScheduleDto(Long id, StudentDto studentDto, TutorDto tutorDto, LocalDateTime startTime, LocalDateTime endTime, String dayOfWeek, String weekOfYear, String status, String scheduleFormat, String address) {
        this.id = id;
        this.studentDto = studentDto;
        this.tutorDto = tutorDto;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
        this.weekOfYear = weekOfYear;
        this.status = status;
        this.scheduleFormat = scheduleFormat;
        this.address = address;
    }

    public ScheduleDto(StudentDto studentDto, TutorDto tutorDto, LocalDateTime startTime, LocalDateTime endTime, String dayOfWeek, String weekOfYear, String status, String scheduleFormat, String address) {
        this.studentDto = studentDto;
        this.tutorDto = tutorDto;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
        this.weekOfYear = weekOfYear;
        this.status = status;
        this.scheduleFormat = scheduleFormat;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StudentDto getStudentDto() {
        return studentDto;
    }

    public void setStudentDto(StudentDto studentDto) {
        this.studentDto = studentDto;
    }

    public TutorDto getTutorDto() {
        return tutorDto;
    }

    public void setTutorDto(TutorDto tutorDto) {
        this.tutorDto = tutorDto;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getWeekOfYear() {
        return weekOfYear;
    }

    public void setWeekOfYear(String weekOfYear) {
        this.weekOfYear = weekOfYear;
    }

    public String getScheduleFormat() {
        return scheduleFormat;
    }

    public void setScheduleFormat(String scheduleFormat) {
        this.scheduleFormat = scheduleFormat;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
