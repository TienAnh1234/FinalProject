package com.example.Comp1640.Entity;


import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "major")
public class Major {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;


    @OneToMany( mappedBy="major",targetEntity = MajorGrade.class, cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<MajorGrade> majorGrades;

    public Major(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Major() {}

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
