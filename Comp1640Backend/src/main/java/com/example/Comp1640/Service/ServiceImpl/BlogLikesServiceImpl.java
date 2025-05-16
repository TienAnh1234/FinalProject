package com.example.Comp1640.Service.ServiceImpl;

import com.example.Comp1640.Entity.Blog;
import com.example.Comp1640.Entity.BlogLike;
import com.example.Comp1640.Entity.User;
import com.example.Comp1640.Repository.BlogRepository;
import com.example.Comp1640.Repository.BlogLikeRepository;
import com.example.Comp1640.Repository.UserRepository;
import com.example.Comp1640.Service.BlogLikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogLikesServiceImpl implements BlogLikesService {

    @Autowired
    private BlogLikeRepository likeRepo;
    @Autowired
    private BlogRepository blogRepo;
    @Autowired
    private UserRepository userRepo;


    @Override
    public String likeOrUnlike(Long userId, Long blogId) {
        User user = userRepo.findById(userId).orElseThrow();
        Blog blog = blogRepo.findById(blogId).orElseThrow();
        if (likeRepo.existsByUserIdAndBlogId(userId, blogId) == 1) {
            likeRepo.deleteByUserIdAndBlogId(user.getId(), blog.getId());
            return "Unliked";
        } else {
            BlogLike like = new BlogLike();
            like.setUser(user);
            like.setBlog(blog);
            likeRepo.save(like);
            return "Liked";
        }
    }

    @Override
    public Long getLikesCount(Long blogId) {
        Blog blog = blogRepo.findById(blogId).orElseThrow();
        return likeRepo.countByBlogId(blog.getId());    }
}
