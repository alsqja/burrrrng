package com.example.burrrrng.controller;

import com.example.burrrrng.constants.Const;
import com.example.burrrrng.dto.LoginRequestDto;
import com.example.burrrrng.dto.UserRequestDto;
import com.example.burrrrng.dto.UserResponseDto;
import com.example.burrrrng.dto.common.CommonResDto;
import com.example.burrrrng.entity.User;
import com.example.burrrrng.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResDto<UserResponseDto>> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.createUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResDto<>("회원가입 완료", userResponseDto));
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResDto<UserResponseDto>> loginUser(@Valid @RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request) {
        User loginedUser = userService.loginUser(loginRequestDto);
        HttpSession session = request.getSession();
        session.setAttribute(Const.LOGIN_USER, loginedUser);

        return ResponseEntity.ok().body(new CommonResDto<>("정상적으로 로그인되었습니다.", UserResponseDto.toDto(loginedUser)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResDto<UserResponseDto>> findUser(@PathVariable Long id) {

        User user = userService.findUserById(id);

        UserResponseDto userResponseDto = UserResponseDto.toDto(user);
        CommonResDto<UserResponseDto> result = new CommonResDto<>("조회 완료되었습니다.", userResponseDto);
        return ResponseEntity.ok().body(result);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommonResDto<UserResponseDto>> updateUser(@PathVariable Long id,
                                                                    @Valid @RequestBody UserRequestDto userRequestDto,
                                                                    HttpSession session) {

        User loginUser = (User) session.getAttribute("loginUser");

        UserResponseDto updatedUser = userService.updateUser(id, userRequestDto, loginUser);
        CommonResDto<UserResponseDto> result = new CommonResDto<>("수정 완료되었습니다.", UserResponseDto.toDto(loginUser));
        return ResponseEntity.ok().body(result);
    }

}
