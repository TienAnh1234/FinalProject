package com.example.Comp1640.DTO;

import com.example.Comp1640.Entity.Tutor;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class IndividualPaymentDto {


    private Long id;

    private String amount;

    private Long tutorId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;


    public IndividualPaymentDto(Long id, String amount, Long tutorId, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.amount = amount;
        this.tutorId = tutorId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public IndividualPaymentDto(String amount, Long tutorId, LocalDateTime startTime, LocalDateTime endTime) {
        this.amount = amount;
        this.tutorId = tutorId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public IndividualPaymentDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Long getTutorId() {
        return tutorId;
    }

    public void setTutorId(Long tutorId) {
        this.tutorId = tutorId;
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
}
