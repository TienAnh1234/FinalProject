package com.example.Comp1640.DTO;

import com.example.Comp1640.Entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

public class AdminDto {

    private Long id;

    private String name;

    private LocalDate birthday;

    private String imageFile;

    private String username;


    public AdminDto(String name, LocalDate birthday, String imageFile, String username) {
        this.name = name;
        this.birthday = birthday;
        this.imageFile = imageFile;
        this.username = username;
    }

    public AdminDto(Long id, String name, LocalDate birthday, String imageFile, String username) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.imageFile = imageFile;
        this.username = username;
    }

    public AdminDto() {
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
