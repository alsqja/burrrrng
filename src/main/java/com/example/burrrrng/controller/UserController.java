package com.example.burrrrng.controller;

import com.example.burrrrng.Service.UserService;
import com.example.burrrrng.dto.LoginRequestDto;
import com.example.burrrrng.dto.UserRequestDto;
import com.example.burrrrng.dto.UserResponseDto;
import com.example.burrrrng.dto.common.CommonResDto;
import com.example.burrrrng.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<CommonResDto<UserResponseDto>> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.createUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResDto<>("회원가입 완료", userResponseDto));
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        User loginedUser = userService.loginUser(loginRequestDto);
        return ResponseEntity.ok().body("정상적으로 로그인되었습니다.");
    }
}
