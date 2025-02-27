package com.assetmanagement.asset.controller;

import com.assetmanagement.asset.dto.User;
import com.assetmanagement.asset.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        // 중복 사용자명 체크
        if (!userService.isUsernameAvailable(user.getUsername())) {
            return ResponseEntity.badRequest().body("이미 존재하는 사용자명입니다.");
        }
        userService.registerUser(user);
        return ResponseEntity.ok("회원가입 성공!");
    }


}
