package com.example.Comp1640.Service;

public interface BlogLikesService {
    String likeOrUnlike(Long userId, Long blogId);
    Long getLikesCount(Long blogId);

}
