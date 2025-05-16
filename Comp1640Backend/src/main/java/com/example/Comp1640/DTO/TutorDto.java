package com.example.Comp1640.DTO;

import com.example.Comp1640.Entity.Tutor;

import java.time.LocalDate;

public class TutorDto {



    private Long id;

    private String name;

    private LocalDate birthday;

    private String imageFile;

    private String username;

    private String status;


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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TutorDto() {}

    public TutorDto(Long id, String name, LocalDate birthday, String imageFile, String username, String status) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.imageFile = imageFile;
        this.username = username;
        this.status = status;

    }

    public TutorDto( Tutor tutor) {
        this.name = tutor.getName();
        this.birthday = tutor.getBirthday();
        this.imageFile = tutor.getImageFile();
        this.username = tutor.getUser().getUsername();
        this.status = tutor.getStatus();
    }

}
