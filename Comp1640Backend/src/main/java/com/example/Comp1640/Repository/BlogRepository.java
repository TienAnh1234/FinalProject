package com.example.Comp1640.Repository;

import com.example.Comp1640.DTO.Dashboard.BlogCountDto;
import com.example.Comp1640.Entity.Admin;
import com.example.Comp1640.Entity.Blog;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    @Query("SELECT new com.example.Comp1640.DTO.Dashboard.BlogCountDto(MONTH(b.createdAt), COUNT(b)) FROM Blog b GROUP BY MONTH(b.createdAt)")
    List<BlogCountDto> countBlogsByMonthAllTime();



    @Modifying
    @Transactional
    @Query(value = "DELETE FROM blog WHERE id = :id", nativeQuery = true)
    void deleteBlogById(@Param("id") Long id);
}
