package com.example.Comp1640.Service;

import com.example.Comp1640.DTO.CommentDto;
import com.example.Comp1640.Entity.Comment;

import java.util.List;

public interface CommentService {

    String saveComment(CommentDto commentDto);

    void deleteComment(Long id);

    List<CommentDto> getAllComment();

    String updateComment(Long id,CommentDto commentDto);
}
