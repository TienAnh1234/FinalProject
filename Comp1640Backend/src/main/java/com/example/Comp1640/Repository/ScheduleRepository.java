package com.example.Comp1640.Repository;

import com.example.Comp1640.Entity.Blog;
import com.example.Comp1640.Entity.Schedule;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {


    @Query(value = "SELECT * FROM schedule WHERE tutor_id = :tutor_id", nativeQuery = true)
    List<Schedule> findByTutorId(@Param("tutor_id") Long tutor_id);

    List<Schedule> findByStudentId(Long studentId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM schedule WHERE tutor_id = :tutorId AND student_id = :studentId", nativeQuery = true)
    void deleteByTutorIdAndStudentId(@Param("tutorId") Long tutorId, @Param("studentId") Long studentId);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM schedule WHERE tutor_id = :tutorId", nativeQuery = true)
    void deleteByTutorId(@Param("tutorId") Long tutorId);


    @Query(value = "SELECT * FROM schedule " +
            "WHERE start_time = :startTime " +
            "AND tutor_id = :tutorId " +
            "AND day_of_week = :dayOfWeek " +
            "AND week_of_year = :weekOfYear " +
            "LIMIT 1",
            nativeQuery = true)
    Optional<Schedule> findFirstMatchingScheduleTutor(
            @Param("startTime") LocalDateTime startTime,
            @Param("tutorId") Long tutorId,
            @Param("dayOfWeek") String dayOfWeek,
            @Param("weekOfYear") String weekOfYear
    );


    @Query(value = "SELECT * FROM schedule " +
            "WHERE start_time = :startTime " +
            "AND student_id = :studentId " +
            "AND day_of_week = :dayOfWeek " +
            "AND week_of_year = :weekOfYear " +
            "LIMIT 1",
            nativeQuery = true)
    Optional<Schedule> findFirstMatchingScheduleStudent(
            @Param("startTime") LocalDateTime startTime,
            @Param("studentId") Long studentId,
            @Param("dayOfWeek") String dayOfWeek,
            @Param("weekOfYear") String weekOfYear
    );



}
