package com.example.Comp1640.Entity;


import com.example.Comp1640.DTO.ScheduleDto;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "studentId", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tutorId", nullable = false)
    private Tutor tutor;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Column(name = "day_of_week")
    private String dayOfWeek; // Thứ (VD: "Thứ Hai")

    @Column(name = "week_of_year")
    private String weekOfYear; // Số tuần trong năm (VD: 14)

    @Column(name = "status")
    private String status;

    @Column(name = "scheduleFormat")
    private String scheduleFormat;

    @Column(name = "address")
    private String address;

    public Schedule() {}

    public Schedule(Long id, Student student, Tutor tutor, LocalDateTime startTime, LocalDateTime endTime, String dayOfWeek, String weekOfYear, String status, String scheduleFormat, String address) {
        this.id = id;
        this.student = student;
        this.tutor = tutor;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
        this.weekOfYear = weekOfYear;
        this.status = status;
        this.scheduleFormat = scheduleFormat;
        this.address = address;
    }

    public Schedule(Student student, Tutor tutor, LocalDateTime startTime, LocalDateTime endTime, String dayOfWeek, String weekOfYear, String status, String scheduleFormat, String address) {
        this.student = student;
        this.tutor = tutor;
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
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
