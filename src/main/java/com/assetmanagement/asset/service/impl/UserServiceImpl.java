package com.assetmanagement.asset.service.impl;

import com.assetmanagement.asset.dto.User;
import com.assetmanagement.asset.mapper.UserMapper;
import com.assetmanagement.asset.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // 비밀번호 해싱
        userMapper.insertUser(user);
    }

    @Override
    public boolean isUsernameAvailable(String username) {
        return userMapper.findByUsername(username) == null;
    }

    @Override
    public void updateRefreshToken(String username, String refreshToken) {
        System.out.println("DB에 저장할 Refresh Token: " + refreshToken);
        System.out.println("대상 사용자: " + username);
        userMapper.updateRefreshToken(username, refreshToken);
    }


    @Override
    public String getRefreshToken(String username) {
        System.out.println("DB에서 가져올 Refresh Token의 사용자: " + username);
        String refreshToken = userMapper.findRefreshTokenByUsername(username);
        System.out.println("DB에서 가져온 Refresh Token: " + refreshToken);
        return refreshToken;
    }

}
