package com.example.Comp1640.DTO;


import com.example.Comp1640.Entity.Major;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;

public class MajorDto {

    private Long id;
    private String name;
    private String description;

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

    @JsonCreator
    public MajorDto(@JsonProperty("id") Long id) {
        this.id = id;
    }

    public MajorDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public MajorDto(Major major) {
        this.id = major.getId();
        this.name = major.getName();
        this.description = major.getDescription();
    }
}
