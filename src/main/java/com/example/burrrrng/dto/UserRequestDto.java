package com.example.burrrrng.dto;

import com.example.burrrrng.config.PasswordEncoder;
import com.example.burrrrng.entity.User;
import com.example.burrrrng.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserRequestDto {

    private final PasswordEncoder passwordEncoder = new PasswordEncoder();

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @Email(message = "Email 형식을 확인해주세요.")
    private String email;

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$",
            message = "비밀번호는 최소 8자 이상이며, 대문자, 소문자, 숫자, 특수문자를 최소 1개씩 포함해야 합니다."
    )
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "주소를 입력해주세요.")
    private String address;

    @NotBlank(message = "필수값입니다.")
    private UserRole role;

    public UserRequestDto(String name, String email, String password, String address, UserRole role) {
       this.name = name;
       this.email = email;
       this.password = password;
       this.address = address;
       this.role = role;
    }

    public User toEntity() {
        return new User(
                this.name,
                this.email,
                passwordEncoder.encode(this.password),
                this.address,
                this.role
                );
    }
}
