package com.example.Comp1640.Service.ServiceImpl;

import com.example.Comp1640.Entity.EmailOtp;
import com.example.Comp1640.Repository.EmailOtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;


@Service
public class EmailOtpService {

    @Autowired
    private EmailOtpRepository otpRepository;

    @Autowired
    private MailService mailService;

    public void sendOtp(String email) {
        String otp = String.format("%06d", new Random().nextInt(999999));
        LocalDateTime expiry = LocalDateTime.now().plusSeconds(120);


        EmailOtp emailOtp = otpRepository.findByEmail(email).orElse(new EmailOtp());
        emailOtp.setEmail(email);
        emailOtp.setOtp(otp);
            emailOtp.setExpiryTime(expiry);
        otpRepository.save(emailOtp);

        mailService.sendMail(email,"OTP authenticattion","Your OTP is " + otp);
    }

    public boolean verifyOtp(String email, String inputOtp) {
        Optional<EmailOtp> record = otpRepository.findByEmail(email);
        if (record.isEmpty()) return false;

        EmailOtp otp = record.get();
        if (otp.getExpiryTime().isBefore(LocalDateTime.now())) {
            otpRepository.delete(otp);
            return false;
        }

        boolean result = otp.getOtp().equals(inputOtp);
        if (result) otpRepository.delete(otp); // Xóa sau khi dùng
        return result;
    }

    public void deleteOtp(String email) {
        EmailOtp otp = otpRepository.findByEmail(email).get();
        otpRepository.delete(otp);
    }

    @Scheduled(fixedRate = 60000) // 60s 1 lần
    public void deleteExpiredEntities() {
        LocalDateTime now = LocalDateTime.now();
        otpRepository.deleteExpired(now);
    }

}
