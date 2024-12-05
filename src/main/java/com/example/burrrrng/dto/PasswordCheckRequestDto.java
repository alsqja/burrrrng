package com.example.burrrrng.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PasswordCheckRequestDto {

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private final String password;

    public PasswordCheckRequestDto(String password) {
        this.password = password;
    }
}
