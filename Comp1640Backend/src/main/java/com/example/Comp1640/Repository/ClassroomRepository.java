package com.example.Comp1640.Repository;

import com.example.Comp1640.Entity.Classroom;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM classroom WHERE student_id = :studentId AND tutor_id = :tutorId", nativeQuery = true)
    void deleteByStudentIdAndTutorId(@Param("studentId") Long studentId, @Param("tutorId") Long tutorId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM classroom WHERE tutor_id = :tutorId", nativeQuery = true)
    void deleteByTutorId(@Param("tutorId") Long tutorId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM classroom WHERE student_id = :studentId", nativeQuery = true)
    void deleteByStudentId(@Param("studentId") Long studentId);



}
