package com.example.Comp1640.Entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "individualpayment")
public class IndividualPayment {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "amount")
    private String amount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tutorId")
    private Tutor tutor;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    public IndividualPayment(Long id, String amount, Tutor tutor, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.amount = amount;
        this.tutor = tutor;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public IndividualPayment(String amount, Tutor tutor, LocalDateTime startTime, LocalDateTime endTime) {
        this.amount = amount;
        this.tutor = tutor;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public IndividualPayment() {}

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
}
