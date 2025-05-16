package com.example.Comp1640.Repository;

import com.example.Comp1640.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    @Query(value = "SELECT id FROM admin WHERE user_id = :id", nativeQuery = true)
    Long findIdByUserId(@Param("id") Long id);

}
