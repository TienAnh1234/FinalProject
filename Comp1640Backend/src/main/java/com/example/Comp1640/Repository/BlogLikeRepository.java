package com.example.Comp1640.Repository;

import com.example.Comp1640.Entity.BlogLike;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BlogLikeRepository extends JpaRepository<BlogLike, Long> {

    @Query(value = "SELECT EXISTS (SELECT 1 FROM post_likes WHERE user_id = :userId AND blog_id = :blogId)", nativeQuery = true)
    Long existsByUserIdAndBlogId(@Param("userId") Long userId, @Param("blogId") Long blogId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM post_likes WHERE user_id = :userId AND blog_id = :blogId", nativeQuery = true)
    void deleteByUserIdAndBlogId(@Param("userId") Long userId, @Param("blogId") Long blogId);

    @Query(value = "SELECT COUNT(*) FROM post_likes WHERE blog_id = :blogId", nativeQuery = true)
    long countByBlogId(@Param("blogId") Long blogId);

}
