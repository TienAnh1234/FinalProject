package com.example.Comp1640.Repository;

import com.example.Comp1640.Entity.Comment;
import com.example.Comp1640.Entity.District;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistrictRepository extends JpaRepository<District, Long> {
}
