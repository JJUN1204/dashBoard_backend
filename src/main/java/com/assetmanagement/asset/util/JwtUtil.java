package com.assetmanagement.asset.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);  //  토큰 서명에 사용할 비밀 키 생성
    private final long EXPIRATION_TIME = 86400000;  //  토큰 만료 시간 (24시간)

    private final long ACCESS_EXPIRATION = 15000;
    private final long  REFRESH_EXPIRATION  = 86400000;

    //  JWT 토큰 생성
//    public String generateToken(String username) {
//        return Jwts.builder()
//                .setSubject(username)  //  사용자 이름 설정
//                .setIssuedAt(new Date())  //  토큰 발급 시간
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))  //  만료 시간 설정
//                .signWith(SECRET_KEY)  //  비밀 키 사용
//                .compact();
//    }
    // Access Token 생성
    public String generateAccessToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION))
                .signWith(SECRET_KEY)
                .compact();
    }

    //  Refresh Token 생성
    public String generateRefreshToken() {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION))
                .signWith(SECRET_KEY)
                .compact();
    }

    //  JWT 토큰 검증 및 사용자 이름 추출
    public String validateAndExtractUsername(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)  //  서명 검증에 사용할 비밀 키 설정
                    .build()
                    .parseClaimsJws(token)  //  토큰의 서명 검증 및 파싱
                    .getBody()
                    .getSubject();  //  토큰에 저장된 사용자 이름 반환
        } catch (JwtException e) {
            return null;  //  토큰이 유효하지 않으면 null 반환
        }
    }
}

