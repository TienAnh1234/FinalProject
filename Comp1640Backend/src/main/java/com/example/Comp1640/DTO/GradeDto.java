package com.example.Comp1640.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class GradeDto {

    private Long id;

    private String name;

    public GradeDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public GradeDto() {}

    public GradeDto(String name) {
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
