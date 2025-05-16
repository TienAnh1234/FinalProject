package com.example.Comp1640.Service.ServiceImpl;

import com.example.Comp1640.DTO.CommentDto;
import com.example.Comp1640.DTO.MajorDto;
import com.example.Comp1640.Entity.Blog;
import com.example.Comp1640.Entity.Comment;
import com.example.Comp1640.Entity.Major;
import com.example.Comp1640.Entity.User;
import com.example.Comp1640.Repository.BlogRepository;
import com.example.Comp1640.Repository.CommentRepository;
import com.example.Comp1640.Repository.UserRepository;
import com.example.Comp1640.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public String saveComment(CommentDto commentDto) {
        User user = userRepository.findById(commentDto.getUserId()).orElse(null);
        Blog blog = blogRepository.findById(commentDto.getBlogId()).orElse(null);
        Comment comment = new Comment(null,commentDto.getContent(),null,blog,user);
        commentRepository.save(comment);

        return "Comment has been saved successfully";
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);

    }

    @Override
    public List<CommentDto> getAllComment() {
        List<Comment> comments =  commentRepository.findAll();
        List<CommentDto> lisCommentDto = new ArrayList<>();

        if (comments.isEmpty()) {
            return null;
        }else{
            for(int i = 0; i < comments.size(); i++){
                CommentDto commentDto = new CommentDto(comments.get(i).getId(),comments.get(i).getContent(),comments.get(i).getCreatedAt(),comments.get(i).getBlog().getId(),comments.get(i).getUser().getId());
                lisCommentDto.add(commentDto);
            }
        }

        return lisCommentDto;
    }

    @Override
    public String updateComment(Long id,CommentDto commentDto) {

        try {
            Comment existingComment = commentRepository.findById(id).orElse(null);
            existingComment.setContent(commentDto.getContent());
            commentRepository.save(existingComment);
            return "Success Updated Comment";
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }
}
