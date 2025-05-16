package com.example.Comp1640.DTO;

import com.example.Comp1640.Entity.Major;
import com.example.Comp1640.Entity.Student;
import com.example.Comp1640.Entity.Tutor;
import com.example.Comp1640.Entity.User;
import jakarta.persistence.*;

import java.time.LocalDate;

public class StudentDto {

    private Long id;

    private String name;

    private LocalDate birthday;

    private String imageFile;

    private String username;

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


    public StudentDto() {}

    public StudentDto(Long id, String name, LocalDate birthday, String imageFile, String username) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.imageFile = imageFile;
        this.username = username;
    }

    public StudentDto( Student student) {
        this.name = student.getName();
        this.birthday = student.getBirthday();
        this.imageFile = student.getImageFile();
        this.username = student.getUser().getUsername();
    }





}
