package com.example.Comp1640.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "postLikes")
public class BlogLike {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "blogId")
    private Blog blog;

    public BlogLike(Long id, User user, Blog blog) {
        this.id = id;
        this.user = user;
        this.blog = blog;
    }

    public BlogLike() {}

    public BlogLike(User user, Blog blog) {
        this.user = user;
        this.blog = blog;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }
}
