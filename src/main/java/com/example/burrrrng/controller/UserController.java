package com.example.burrrrng.controller;

import com.example.burrrrng.constants.Const;
import com.example.burrrrng.dto.LoginRequestDto;
import com.example.burrrrng.dto.PasswordCheckRequestDto;
import com.example.burrrrng.dto.PasswordUpdateRequestDto;
import com.example.burrrrng.dto.OrderAllListResDto;
import com.example.burrrrng.dto.UserRequestDto;
import com.example.burrrrng.dto.UserResponseDto;
import com.example.burrrrng.dto.UserUpdateRequestDto;
import com.example.burrrrng.dto.common.CommonNoContentResDto;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

        //  SESSION, COOKIE 처리
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

    @PostMapping("/{id}/password-check")
    public ResponseEntity<CommonNoContentResDto> checkPassword(
            @PathVariable Long id,
            @Valid @RequestBody PasswordCheckRequestDto passwordCheckRequestDto,
            @SessionAttribute(name = Const.LOGIN_USER) User loginUser,
            HttpServletRequest request
    ) {
        userService.checkPassword(id, passwordCheckRequestDto.getPassword(), loginUser);    // checkPassword -> false는 THROW, TRUE 는 실행(다음줄로 넘어감)

        //  session 처리 REQUEST 사용 -> 로그인 세션 로직과 동일
        HttpSession session = request.getSession();
        session.setAttribute(Const.PASSWORD_CHECK, Boolean.TRUE);

        return ResponseEntity.status(HttpStatus.OK).body(new CommonNoContentResDto("비밀번호 일치 확인"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonNoContentResDto> deleteUser(
            @PathVariable Long id,
            @SessionAttribute(name = Const.LOGIN_USER) User loginUser,
            @SessionAttribute(name = Const.PASSWORD_CHECK, required = false) Boolean isChecked,
            HttpServletRequest request
    ) {
        if (isChecked == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호를 확인해주세요.");
        }



        if (!loginUser.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인만 삭제할 수 있습니다.");
        }

        userService.deleteUser(id, loginUser);

        //  logout();
        logout(request);

        return ResponseEntity.status(HttpStatus.OK).body(new CommonNoContentResDto("정상적으로 삭제되었습니다."));
    }

    //  public --- logout() {}
    @PostMapping("/logout")
    public ResponseEntity<CommonNoContentResDto> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return ResponseEntity.status(HttpStatus.OK).body(new CommonNoContentResDto("로그아웃 되었습니다."));

    }
}
