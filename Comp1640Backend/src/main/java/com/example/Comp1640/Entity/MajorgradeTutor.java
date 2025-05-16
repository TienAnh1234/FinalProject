package com.example.Comp1640.Entity;


import jakarta.persistence.*;

@Entity
@Table(name = "MajorgradeTutor")
public class MajorgradeTutor {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "majorGradeId")
    private MajorGrade majorGrade;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tutorId")
    private Tutor tutor;

    public MajorgradeTutor(Long id, MajorGrade majorGrade, Tutor tutor) {
        this.id = id;
        this.majorGrade = majorGrade;
        this.tutor = tutor;
    }

    public MajorgradeTutor(MajorGrade majorGrade, Tutor tutor) {
        this.majorGrade = majorGrade;
        this.tutor = tutor;
    }

    public MajorgradeTutor() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MajorGrade getMajorGrade() {
        return majorGrade;
    }

    public void setMajorGrade(MajorGrade majorGrade) {
        this.majorGrade = majorGrade;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }
}
