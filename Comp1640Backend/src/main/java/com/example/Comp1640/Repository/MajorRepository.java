package com.example.Comp1640.Repository;

import com.example.Comp1640.Entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MajorRepository extends JpaRepository<Major, Long> {

    @Query(value = "SELECT id FROM major WHERE name = :majorName", nativeQuery = true)
    Long findIdByMajorname(@Param("majorName") String majorName);

}
