package com.example.Comp1640.Config.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JwtUtils {

    private static final String SECRET_KEY = "tienanh";
    private static final long EXPIRATION_TIME = 30 * 60 * 1000;


    // Tạo Access Token
    public static String generateToken(String username, String role) {
        return JWT.create()
                .withSubject(username)
                .withClaim("role", role)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    // Giải mã JWT để lấy thông tin
    public static DecodedJWT verifyToken(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .build()
                .verify(token);
    }


    // Kiểm tra xem token có hợp lệ không
    public static boolean isValidToken(String token) {
        try {
            verifyToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Lấy username từ JWT
    public static String extractUsername(String token) {
        return verifyToken(token).getSubject();
    }

    // Lấy role từ JWT
    public static String extractRole(String token) {
        return verifyToken(token).getClaim("role").asString();
    }

}
