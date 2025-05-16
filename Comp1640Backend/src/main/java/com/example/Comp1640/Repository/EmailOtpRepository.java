package com.example.Comp1640.Repository;

import com.example.Comp1640.Entity.EmailOtp;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailOtpRepository extends JpaRepository<EmailOtp, Long> {
    Optional<EmailOtp> findByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM email_otp WHERE expiry_time < :now", nativeQuery = true)
    void deleteExpired(@Param("now") LocalDateTime now);

}
