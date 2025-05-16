package com.example.Comp1640.DTO;

import com.example.Comp1640.Entity.Blog;
import com.example.Comp1640.Entity.User;
import jakarta.persistence.*;

public class PostLikeDto {

    private Long id;

    private UserDto userDto;

    private BlogDto blogDto;

    public PostLikeDto(Long id, UserDto userDto, BlogDto blogDto) {
        this.id = id;
        this.userDto = userDto;
        this.blogDto = blogDto;
    }

    public PostLikeDto(UserDto userDto, BlogDto blogDto) {
        this.userDto = userDto;
        this.blogDto = blogDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public BlogDto getBlogDto() {
        return blogDto;
    }

    public void setBlogDto(BlogDto blogDto) {
        this.blogDto = blogDto;
    }
}
