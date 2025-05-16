package com.example.Comp1640.Repository;

import com.example.Comp1640.DTO.Dashboard.MajorCountDto;
import com.example.Comp1640.Entity.Student;
import com.example.Comp1640.Entity.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value = "SELECT id FROM student WHERE user_id = :id", nativeQuery = true)
    Long findIdByUserId(@Param("id") Long id);

    @Query(value = """
    SELECT DISTINCT s.*
    FROM student s
    JOIN user u ON s.user_id = u.id
    JOIN district d ON u.id_district = d.id
    WHERE (:username IS NULL OR s.name  LIKE %:username%)
      AND (:id_district IS NULL OR d.id = :id_district)
""", nativeQuery = true)
    List<Student> findStudentsByDynamicCriteriaNative(
            @Param("username") String username,
            @Param("id_district") Long id_district

    );

}
