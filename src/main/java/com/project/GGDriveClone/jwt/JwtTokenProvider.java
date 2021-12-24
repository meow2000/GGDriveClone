package com.project.GGDriveClone.jwt;

import com.project.GGDriveClone.security.CustomUserDetails;
import io.jsonwebtoken.*;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    // Đoạn JWT_SECRET này là bí mật, chỉ có phía server biết
    private final String JWT_SECRET = "lodaaaaaa";
    //Thời gian có hiệu lực của chuỗi jwt
    private final long JWT_EXPIRATION = 604800000L;
    private org.slf4j.Logger Logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    // Tạo ra jwt từ thông tin user
    public String generateToken(CustomUserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        // Tạo chuỗi json web token từ id của user.
        return Jwts.builder()
                .setSubject(Long.toString(userDetails.getUserId())) //null here
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    // Lấy thông tin user từ jwt
    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            Logger.info("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            Logger.info("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            Logger.info("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            Logger.info("JWT claims string is empty.");
        }
        return false;
    }
}
