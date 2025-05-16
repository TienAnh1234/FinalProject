package com.example.Comp1640.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;


@Entity
@Table(name = "blog")
public class Blog {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user")
    private User user;

    @Column(name = "status")
    private String status;

    @CreationTimestamp
    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @OneToMany( mappedBy="blog",targetEntity = BlogLike.class, cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<BlogLike> postLikes;

    @OneToMany( mappedBy="blog",targetEntity = Comment.class, cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Comment> comments;


    @Lob
    @Column(name = "file_data", columnDefinition = "LONGBLOB")
    private byte[] fileData;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;


    public Blog(){}

    public Blog(Long id, String content, User user,String status, LocalDateTime createdAt, LocalDateTime updatedAt, Set<BlogLike> postLikes, byte[] fileData, String fileName, String fileType) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.postLikes = postLikes;
        this.fileData = fileData;
        this.fileName = fileName;
        this.fileType = fileType;
    }

    public Blog(String content, User user,String status, LocalDateTime createdAt, LocalDateTime updatedAt, Set<BlogLike> postLikes, byte[] fileData, String fileName, String fileType) {
        this.content = content;
        this.user = user;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.postLikes = postLikes;
        this.fileData = fileData;
        this.fileName = fileName;
        this.fileType = fileType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<BlogLike> getLikes() {
        return postLikes;
    }

    public void setLikes(Set<BlogLike> postLikes) {
        this.postLikes = postLikes;
    }

    public Set<BlogLike> getPostLikes() {
        return postLikes;
    }

    public void setPostLikes(Set<BlogLike> postLikes) {
        this.postLikes = postLikes;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
