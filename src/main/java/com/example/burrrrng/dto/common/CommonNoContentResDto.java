package com.example.burrrrng.dto.common;

import lombok.Getter;

@Getter
public class CommonNoContentResDto {
    private final String message;

    public CommonNoContentResDto(String message) {
        this.message = message;
    }
}
