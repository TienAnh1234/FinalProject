package com.example.Comp1640.Repository;

import com.example.Comp1640.DTO.MonthlyTotalDto;
import com.example.Comp1640.Entity.IndividualPayment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface IndividualPaymentRepository extends JpaRepository<IndividualPayment, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM individualpayment WHERE end_time < :now", nativeQuery = true)
    void deleteExpired(@Param("now") LocalDateTime now);

    @Query(value = "SELECT * FROM individualpayment WHERE end_time < :now", nativeQuery = true)
    List<IndividualPayment> findExpired(@Param("now") LocalDateTime now);

    @Query("SELECT new com.example.Comp1640.DTO.MonthlyTotalDto(MONTH(p.startTime), SUM(CAST(p.amount AS long ))) " +
            "FROM IndividualPayment p " +
            "WHERE p.amount IS NOT NULL " +
            "GROUP BY MONTH(p.startTime) " +
            "ORDER BY MONTH(p.startTime)")
    List<MonthlyTotalDto> findTotalAmountByMonth();
}
