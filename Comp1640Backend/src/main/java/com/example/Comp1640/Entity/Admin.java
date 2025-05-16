package com.example.Comp1640.Entity;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "admin")
public class Admin {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    private LocalDate birthday;

    private String imageFile;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    public Admin( String name, LocalDate birthday, String imageFile, User user) {
        this.name = name;
        this.birthday = birthday;
        this.imageFile = imageFile;
        this.user = user;
    }

    public Admin() {}

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
