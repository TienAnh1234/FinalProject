package com.example.Comp1640.Service.ServiceImpl;

import com.example.Comp1640.Config.Security.JwtUtils;
import com.example.Comp1640.DTO.DefinedUser;
import com.example.Comp1640.DTO.ResetPassword;
import com.example.Comp1640.Entity.LoginRequest;
import com.example.Comp1640.Entity.OtpVerifyRequest;
import com.example.Comp1640.Entity.User;
import com.example.Comp1640.Repository.UserRepository;
import com.example.Comp1640.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailOtpService otpService;

    @Override
    public String login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(checkPassword(request.getPassword(),user.getPassword())){
            return JwtUtils.generateToken(user.getUsername(), user.getRole().name());
        }else{
            return "Wrong";
//            return JwtUtils.generateToken(user.getUsername(), user.getRole().name());
        }
    }

    @Override
    public String register(DefinedUser definedUser) {
        otpService.sendOtp(definedUser.getEmail());
        return "OTP sent";
    }


    @Override
    public String verify(OtpVerifyRequest req) {
        boolean valid = otpService.verifyOtp(req.getEmail(), req.getOtp());
        return valid ? "Verified" :
                "Invalid or expired OTP";
    }

    @Override
    public void resetPassword(ResetPassword req) {
        User user = userRepository.findByUsernameAndEmail(req.getUsername(), req.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(req.getNewPassword()));
        userRepository.save(user);
    }


    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }



}
