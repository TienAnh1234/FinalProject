package com.example.Comp1640.Entity;


import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;



    @OneToOne(mappedBy = "user",targetEntity = Student.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Student student;

    @OneToOne(mappedBy = "user",targetEntity = Tutor.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Tutor tutor;

    @OneToOne(mappedBy = "user",targetEntity = Admin.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Admin admin;

    @OneToMany( mappedBy="user",targetEntity = Blog.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Blog> blogs;

    @OneToMany( mappedBy="user",targetEntity = BlogLike.class, cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<BlogLike> postLikes;

    @OneToMany( mappedBy="user",targetEntity = Comment.class, cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Comment> comments;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_district")
    private District district;



    public User( String username, String email, String password, Role role, Student student, Tutor tutor, Admin admin) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.student = student;
        this.tutor = tutor;
        this.admin = admin;
    }

    public User(String username, String email, String password, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }
}
