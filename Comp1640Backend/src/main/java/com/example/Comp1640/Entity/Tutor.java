package com.example.Comp1640.Entity;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "tutor")
public class Tutor {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    private LocalDate birthday;

    private String imageFile;

    private String status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", unique = true)
    private User user;


    @OneToMany( mappedBy="tutor",targetEntity = Classroom.class, cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Classroom> classrooms;

    @OneToMany( mappedBy="tutor",targetEntity = Schedule.class, cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Schedule> schedules;

    @OneToMany( mappedBy="tutor",targetEntity = MajorgradeTutor.class, cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<MajorgradeTutor> majorgradeTutors;

    @OneToMany( mappedBy="tutor",targetEntity = IndividualPayment.class, cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<IndividualPayment> individualPayments;

    public Tutor( String name, LocalDate birthday, User user, String status) {
        this.name = name;
        this.birthday = birthday;
        this.user = user;
        this.status = status;
    }

    public Tutor() {}

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
