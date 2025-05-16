package com.example.Comp1640.Repository;

import com.example.Comp1640.DTO.Dashboard.MajorCountDto;
import com.example.Comp1640.DTO.SearchStudentOrTutor;
import com.example.Comp1640.Entity.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TutorRepository extends JpaRepository<Tutor, Long> {

    @Query(value = "SELECT id FROM tutor WHERE user_id = :id", nativeQuery = true)
    Long findIdByUserId(@Param("id") Long id);

    @Query(value = """
    SELECT DISTINCT t.*
    FROM tutor t
    JOIN user u ON t.user_id = u.id
    JOIN district d ON u.id_district = d.id
    JOIN majorgrade_tutor mgt ON t.id = mgt.tutor_id
    JOIN major_grade mg ON mgt.major_grade_id = mg.id
    WHERE (:username IS NULL OR t.name LIKE %:username%)
      AND (:id_district IS NULL OR d.id = :id_district)
      AND (:gradeId IS NULL OR mg.grade_id = :gradeId)
      AND (:majorId IS NULL OR mg.major_id = :majorId)
""", nativeQuery = true)
    List<Tutor> findTutorsByDynamicCriteriaNative(
            @Param("username") String username,
            @Param("id_district") Long id_district,
            @Param("gradeId") Long gradeId,
            @Param("majorId") Long majorId
    );



}
