package com.example.auth_service.Sercurity;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    private static String SECRET_KEY = "your-secret-key-your-secret-key-your-secret-key"; // 256-bit
    private final long EXPIRATION_TIME = 86400000; // 24 giờ

    private static  SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String username, String email, String role) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(username)
                .claim("email", email)  // Thêm email vào token
                .claim("role", role)    // Thêm role vào token
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
//extract information from token.
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public String extractEmail(String token) {
       return extractClaim(token, Claims->Claims.get("email", String.class));
    }
    public String extractRole(String token) {
        return extractClaim(token,Claims->Claims.get("role",String.class));
    }


    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

    public static boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token); // Nếu token hợp lệ, không có exception nào xảy ra
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token đã hết hạn!");
        } catch (UnsupportedJwtException e) {
            System.out.println("Token không được hỗ trợ!");
        } catch (MalformedJwtException e) {
            System.out.println("Token không hợp lệ!");
        } catch (SignatureException e) {
            System.out.println("Chữ ký không đúng!");
        } catch (IllegalArgumentException e) {
            System.out.println("Token rỗng hoặc null!");
        }
        return false;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
