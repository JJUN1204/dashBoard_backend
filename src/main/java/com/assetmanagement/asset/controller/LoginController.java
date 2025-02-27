package com.assetmanagement.asset.controller;

import com.assetmanagement.asset.service.UserService;
import com.assetmanagement.asset.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;


    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping("/login")
//    public Map<String, String> login(@RequestBody Map<String, String> loginRequest) {
//        String username = loginRequest.get("username");
//        String password = loginRequest.get("password");
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(username, password)
//        );
//
//
//
//        String token = jwtUtil.generateToken(username);
//
//        Map<String, String> response = new HashMap<>();
//        response.put("token", token);
//        return response;
//    }



    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        //  사용자 인증
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        //  Access Token & Refresh Token 생성
        String accessToken = jwtUtil.generateAccessToken(username);
        String refreshToken = jwtUtil.generateRefreshToken();

        //  Refresh Token을 DB에 저장
        userService.updateRefreshToken(username, refreshToken);

        Map<String, String> response = new HashMap<>();
        response.put("accessToken", accessToken);
        response.put("refreshToken", refreshToken);
        response.put("username" , username);
        return response;
    }


    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        String username = request.get("username");

        System.out.println("요청된 Refresh Token: " + refreshToken);
        System.out.println("요청된 Username: " + username);

        // DB에서 저장된 Refresh Token 조회
        String storedRefreshToken = userService.getRefreshToken(username);

        System.out.println("DB에 저장된 Refresh Token: " + storedRefreshToken);

        if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
            System.out.println("Refresh Token이 일치하지 않음!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Refresh Token");
        }

        //새로운 Access Token 발급
        String newAccessToken = jwtUtil.generateAccessToken(username);

        System.out.println("새로 발급된 Access Token: " + newAccessToken);

        Map<String, String> response = new HashMap<>();
        response.put("accessToken", newAccessToken);
        return ResponseEntity.ok(response);
    }



}
