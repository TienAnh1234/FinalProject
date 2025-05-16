package com.example.Comp1640.Service;

import com.example.Comp1640.DTO.DefinedUser;
import com.example.Comp1640.DTO.ResetPassword;
import com.example.Comp1640.Entity.LoginRequest;
import com.example.Comp1640.Entity.OtpVerifyRequest;

public interface AuthService {

    String login(LoginRequest request);
    String register(DefinedUser definedUser);
    String verify(OtpVerifyRequest req);
    void resetPassword(ResetPassword req);
}
