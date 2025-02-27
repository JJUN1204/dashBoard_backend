package com.assetmanagement.asset.service;

import com.assetmanagement.asset.dto.User;

public interface UserService {
    void registerUser(User user); // 유저 등록
    boolean isUsernameAvailable(String username); // 유저 중복 확인
    void updateRefreshToken(String username, String refreshToken); // 리프레시 토큰 저장
    String getRefreshToken(String username); // 리프레시 토큰 조회
}
