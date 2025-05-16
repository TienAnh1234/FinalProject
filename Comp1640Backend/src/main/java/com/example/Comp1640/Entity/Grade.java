package com.example.Comp1640.Entity;

import jakarta.persistence.*;

import java.util.Set;


@Entity
@Table(name = "grade")
public class Grade {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany( mappedBy="grade",targetEntity = MajorGrade.class, cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<MajorGrade> majorGrades;

    public Grade() {}

    public Grade(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Grade(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
