package com.example.Comp1640.Controller;


import com.example.Comp1640.DTO.BlogDto;
import com.example.Comp1640.DTO.CommentDto;

import com.example.Comp1640.DTO.MajorDto;
import com.example.Comp1640.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping()
    public ResponseEntity<List<CommentDto>> getAllCommentDto() {
        return ResponseEntity.ok(commentService.getAllComment());
    }


    @PostMapping("/save_comment")
    public ResponseEntity<String> saveNew(@RequestBody(required = false) CommentDto commentDto) {
        return ResponseEntity.ok(commentService.saveComment(commentDto));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateComment(@PathVariable Long id, @RequestBody(required = false) CommentDto commentDto ) {
        return ResponseEntity.ok(commentService.updateComment(id, commentDto));
    }
}
