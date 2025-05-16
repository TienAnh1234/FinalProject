package com.example.Comp1640.Controller;


import com.example.Comp1640.Service.BlogLikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/blogLikes")
public class BlogLikeController {

    @Autowired
    private BlogLikesService blogLikesService;

    @PostMapping("/{userId}/{blogId}")
    public ResponseEntity<String> toggleLike(@PathVariable Long userId, @PathVariable Long blogId) {
        String result = blogLikesService.likeOrUnlike(userId, blogId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/count/{blogId}")
    public ResponseEntity<Long> getLikesCount(@PathVariable Long blogId) {
        return ResponseEntity.ok(blogLikesService.getLikesCount(blogId));
    }

}
