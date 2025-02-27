package com.assetmanagement.asset.mapper;

import com.assetmanagement.asset.dto.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    User findByUsername(@Param("username") String username);

    void insertUser(User user);

    void updateRefreshToken(@Param("username") String username, @Param("refreshToken") String refreshToken);

    String findRefreshTokenByUsername(@Param("username") String username);
}

