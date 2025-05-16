package com.example.Comp1640.Repository;

import com.example.Comp1640.Entity.Classroom;
import com.example.Comp1640.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
