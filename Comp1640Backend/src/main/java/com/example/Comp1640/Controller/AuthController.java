package com.example.Comp1640.Controller;


import com.example.Comp1640.DTO.DefinedUser;
import com.example.Comp1640.DTO.ResetPassword;
import com.example.Comp1640.Entity.LoginRequest;
import com.example.Comp1640.Entity.OtpVerifyRequest;
import com.example.Comp1640.Service.AuthService;
import com.example.Comp1640.Service.ServiceImpl.EmailOtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private EmailOtpService emailOtpService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        System.out.println(request.getUsername());
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody DefinedUser definedUser) {
        return  ResponseEntity.ok(authService.register(definedUser));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verify(@RequestBody OtpVerifyRequest req) {
        return ResponseEntity.ok(authService.verify(req));
    }

    @PostMapping("/resetpassword")
    public void resetPassword(@RequestBody ResetPassword req) {
         authService.resetPassword(req);
    }

    @PostMapping("/sendOtp")
    public void sendOtp(@RequestBody ResetPassword req) {
        System.out.println(req.getEmail());
        System.out.println(req.getNewPassword());
        System.out.println(req.getUsername());

        emailOtpService.sendOtp(req.getEmail());
    }


}
