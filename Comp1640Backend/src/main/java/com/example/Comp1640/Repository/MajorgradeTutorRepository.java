package com.example.Comp1640.Repository;

import com.example.Comp1640.DTO.PopularSubjectProjection;
import com.example.Comp1640.Entity.EmailOtp;
import com.example.Comp1640.Entity.MajorgradeTutor;
import com.example.Comp1640.Entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MajorgradeTutorRepository extends JpaRepository<MajorgradeTutor, Long> {

    List<MajorgradeTutor> findByTutorId(Long tutorId);

    @Query(value = "SELECT COUNT(*) FROM majorgrade_tutor    WHERE major_grade_id = :majorGradeId AND tutor_id = :tutorId", nativeQuery = true)
    Long countExisted(@Param("majorGradeId") Long majorGradeId, @Param("tutorId") Long tutorId);


    @Query(value = "SELECT * FROM majorgrade_tutor WHERE major_grade_id = :majorGradeId AND tutor_id = :tutorId", nativeQuery = true)
    Optional<MajorgradeTutor> findByMajorGradeIdAndTutorId(@Param("majorGradeId") Long majorGradeId, @Param("tutorId") Long tutorId);

    @Query(value = """
        SELECT 
            m.name AS majorName,
            g.name AS gradeName,
            COUNT(mgt.tutor_id) AS tutorCount
        FROM major m
        JOIN major_grade mg ON m.id = mg.major_id
        JOIN grade g ON g.id = mg.grade_id
        JOIN majorgrade_tutor mgt ON mg.id = mgt.major_grade_id
        GROUP BY m.name, g.name
        ORDER BY tutorCount DESC
        """, nativeQuery = true)
    List<PopularSubjectProjection> findMostPopularMajorWithGrade();
}
