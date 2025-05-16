package com.example.Comp1640.Entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "district")
public class District {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "district",targetEntity = User.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<User> users;

    public District(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    public District(String name) {
        this.name = name;
    }

    public District() {}

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
