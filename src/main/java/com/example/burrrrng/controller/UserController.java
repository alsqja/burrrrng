package com.example.burrrrng.controller;

import com.example.burrrrng.constants.Const;
import com.example.burrrrng.dto.LoginRequestDto;
import com.example.burrrrng.dto.PasswordUpdateRequestDto;
import com.example.burrrrng.dto.OrderAllListResDto;
import com.example.burrrrng.dto.UserRequestDto;
import com.example.burrrrng.dto.UserResponseDto;
import com.example.burrrrng.dto.UserUpdateRequestDto;
import com.example.burrrrng.dto.common.CommonResDto;
import com.example.burrrrng.entity.User;
import com.example.burrrrng.service.OrderService;
import com.example.burrrrng.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final OrderService orderService;

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
                                                                    @Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto,
                                                                    HttpSession session) {

        User loginUser = (User) session.getAttribute("loginUser");

        UserResponseDto updatedUser = userService.updateUser(id, userUpdateRequestDto, loginUser);
        CommonResDto<UserResponseDto> result = new CommonResDto<>("수정 완료되었습니다.", updatedUser);
        return ResponseEntity.ok().body(result);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
//        userService.deleteUser(id);
//        return ResponseEntity.ok().body("정상적으로 삭제되었습니다.");
//    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<CommonResDto<UserResponseDto>> updatePassword(@PathVariable Long id,
                                                                        @Valid @RequestBody PasswordUpdateRequestDto passwordUpdateRequestDto,
                                                                        @SessionAttribute(name = Const.LOGIN_USER) User loginUser) {

        userService.updatePassword(id, passwordUpdateRequestDto, loginUser);

        return ResponseEntity.ok().body(new CommonResDto<>("비밀번호가 변경되었습니다.", null));
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<CommonResDto<OrderAllListResDto>> findAllUserOrder(@PathVariable Long id) {
        return ResponseEntity.ok().body(new CommonResDto<>("주문 내역 받기 완료", new OrderAllListResDto(orderService.findAllUserOrder(id))));
    }
}
