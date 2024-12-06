package com.example.burrrrng.dto;

import com.example.burrrrng.dto.common.ResDtoDataType;
import com.example.burrrrng.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto implements ResDtoDataType {
    private final Long id;
    private final String name;
    private final String email;
    private final String address;
    private final String role;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public UserResponseDto(Long id, String name, String email, String address, String role, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAddress(),
                user.getRole().getValue(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
