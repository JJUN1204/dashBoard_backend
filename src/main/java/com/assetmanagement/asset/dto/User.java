package com.assetmanagement.asset.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class User {
    private Long id;

    @NotBlank(message = "유저 이름은 필수 입력 항목입니다")
    @Size(min = 5, max = 20, message = "유저이름은 5글자 이상 20글자 이하로 입력해주세요")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다")
    @Size(min = 5, max = 20, message = "비밀번호는 5글자 이상 20글자 이하로 입력해주세요")
    private String password;

    private String refreshToken;
}
