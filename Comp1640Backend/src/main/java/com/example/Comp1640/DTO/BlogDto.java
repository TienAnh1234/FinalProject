package com.example.Comp1640.DTO;

import com.example.Comp1640.Entity.Blog;
import com.example.Comp1640.Entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

public class BlogDto {

    private Long id;

    private String content;

    private String status;

    private UserDto userDto;

    private Long userId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // ✅ THÊM 3 THUỘC TÍNH MỚI
    private String fileName;
    private String fileType;
    private byte[] fileData; // optional, dùng nếu cần hiển thị preview hình ảnh PDF

    public BlogDto() {}

    public BlogDto(Long id, String content, UserDto userDto, LocalDateTime createdAt, LocalDateTime updatedAt,String fileName, String fileType, byte[] fileData) {
        this.id = id;
        this.content = content;
        this.userDto = userDto;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileData = fileData;
    }

    public BlogDto(String content, UserDto userDto, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.content = content;
        this.userDto = userDto;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public BlogDto(Blog blog) {
        this.id = blog.getId();
        this.content = blog.getContent();
        this.status = blog.getStatus();
        this.userDto = new UserDto(blog.getUser());
        this.createdAt = blog.getCreatedAt();
        this.updatedAt = blog.getUpdatedAt();
        this.fileName = blog.getFileName(); // ✅ Lấy từ Entity
        this.fileType = blog.getFileType();
        this.fileData = blog.getFileData();
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

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
