package com.example.Comp1640.DTO;

import com.example.Comp1640.Entity.Blog;
import com.example.Comp1640.Entity.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class CommentDto {

    private Long id;

    private String content;

    private LocalDateTime createdAt;

    private Long blogId;

    private Long userId;

    public CommentDto(Long id, String content, LocalDateTime createdAt, Long blogId, Long userId) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.blogId = blogId;
        this.userId = userId;
    }

    public CommentDto(String content, LocalDateTime createdAt, Long blogId, Long userId) {
        this.content = content;
        this.createdAt = createdAt;
        this.blogId = blogId;
        this.userId = userId;
    }

    public CommentDto() {}

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
