package com.example.Comp1640.Entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "majorGrade")
public class MajorGrade {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "majorId")
    private Major major;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gradeId")
    private Grade grade;

    @OneToMany( mappedBy="majorGrade",targetEntity = MajorgradeTutor.class, cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<MajorgradeTutor> majorgradeTutors;


    public MajorGrade() {}

    public MajorGrade(Long id, Major major, Grade grade) {
        this.id = id;
        this.major = major;
        this.grade = grade;
    }

    public MajorGrade(Major major, Grade grade) {
        this.major = major;
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}
