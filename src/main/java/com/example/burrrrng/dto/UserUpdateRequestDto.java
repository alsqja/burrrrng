package com.example.burrrrng.dto;

import lombok.Getter;

@Getter
public class UserUpdateRequestDto {
    
    private final String name;
    private final String address;

    public UserUpdateRequestDto(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
