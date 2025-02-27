package com.assetmanagement.asset.service.impl;

import com.assetmanagement.asset.dto.User;
import com.assetmanagement.asset.mapper.UserMapper;
import com.assetmanagement.asset.service.CustomUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
    private final UserMapper userMapper;

    public CustomUserDetailsServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUsername(username); //유저 정보 조회
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword()) //스프링 시큐리티에서 자동으로 입력한 값이 해싱한 값과 일치한지 비교함
                .build(); // 객체 생성해서 반환
    }
}
