package com.example.Comp1640.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;


@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "image_file")
    private String imageFile;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", unique = true)
    private User user;


    @OneToMany( mappedBy="student",targetEntity = Classroom.class, cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Classroom> classrooms;

    @OneToMany( mappedBy="student",targetEntity = Schedule.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Schedule> schedules;

    public Student( String name, LocalDate birthday, User user) {
        this.name = name;
        this.birthday = birthday;
        this.user = user;
    }

    public Student() {}

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
