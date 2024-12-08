package com.example.burrrrng.service;

import com.example.burrrrng.config.PasswordEncoder;
import com.example.burrrrng.dto.LoginRequestDto;
import com.example.burrrrng.dto.PasswordUpdateRequestDto;
import com.example.burrrrng.dto.UserRequestDto;
import com.example.burrrrng.dto.UserResponseDto;
import com.example.burrrrng.dto.UserUpdateRequestDto;
import com.example.burrrrng.entity.User;
import com.example.burrrrng.enums.UserRole;
import com.example.burrrrng.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    void 회원가입_예외() {
        UserRequestDto dto1 = new UserRequestDto("testName", "test@email.com", "Alsqja@0000", "testAddress", UserRole.USER);
        UserRequestDto dto2 = new UserRequestDto("testName", "test@email.com", "Alsqja@0000", "testAddress", UserRole.USER);

        UserResponseDto saveUser = userService.createUser(dto1);

        ResponseStatusException saveErr = assertThrows(ResponseStatusException.class, () -> userService.createUser(dto2));

        Optional<User> findUser = userRepository.findById(saveUser.getId());

        assertThat(findUser.isPresent()).isEqualTo(true);
        assertThat(findUser.get().getEmail()).isEqualTo("test@email.com");
        assertThat(saveErr.getReason()).isEqualTo("이미 존재하는 이메일 입니다.");
    }

    @Test
    void 로그인_예외() {
        UserResponseDto saveUser = userService.createUser(new UserRequestDto("testName", "test@email.com", "Alsqja@0000", "testAddress", UserRole.USER));

        LoginRequestDto dto = new LoginRequestDto("test@email.com", "Alsqja@0000");
        LoginRequestDto emailErrDto = new LoginRequestDto("wrong@email.com", "Alsqja@0000");
        LoginRequestDto passwordErrDto = new LoginRequestDto("test@email.com", "Alsqja");

        User loginUser = userService.loginUser(dto);
        ResponseStatusException emailErr = assertThrows(ResponseStatusException.class, () -> userService.loginUser(emailErrDto));
        ResponseStatusException passwordErr = assertThrows(ResponseStatusException.class, () -> userService.loginUser(passwordErrDto));

        assertThat(loginUser.getName()).isEqualTo("testName");
        assertThat(emailErr.getReason()).isEqualTo("존재하지 않는 이메일입니다.");
        assertThat(passwordErr.getReason()).isEqualTo("잘못된 비밀번호입니다.");
    }

    @Test
    void 유저정보수정_예외() {
        UserResponseDto saveUser1 = userService.createUser(new UserRequestDto("testName", "test1@email.com", "Alsqja@0000", "testAddress", UserRole.USER));
        User user1 = userRepository.findByIdOrElseThrow(saveUser1.getId());
        UserResponseDto saveUser2 = userService.createUser(new UserRequestDto("testName", "test2@email.com", "Alsqja@0000", "testAddress", UserRole.USER));
        User user2 = userRepository.findByIdOrElseThrow(saveUser2.getId());
        UserResponseDto saveUser3 = userService.createUser(new UserRequestDto("testName", "test3@email.com", "Alsqja@0000", "testAddress", UserRole.USER));
        User user3 = userRepository.findByIdOrElseThrow(saveUser3.getId());

        UserUpdateRequestDto dto1 = new UserUpdateRequestDto("patchTestName", "patchTestAddress");
        UserUpdateRequestDto dto2 = new UserUpdateRequestDto("patchTestName", null);
        UserUpdateRequestDto dto3 = new UserUpdateRequestDto(null, "patchTestAddress");

        assertThat(userService.updateUser(saveUser1.getId(), dto1, user1).getName()).isEqualTo("patchTestName");
        assertThat(userService.updateUser(saveUser1.getId(), dto1, user1).getAddress()).isEqualTo("patchTestAddress");

        assertThat(userService.updateUser(saveUser2.getId(), dto2, user2).getAddress()).isEqualTo("testAddress");
        assertThat(userService.updateUser(saveUser2.getId(), dto2, user2).getName()).isEqualTo("patchTestName");

        assertThat(userService.updateUser(saveUser3.getId(), dto3, user3).getName()).isEqualTo("testName");
        assertThat(userService.updateUser(saveUser3.getId(), dto3, user3).getAddress()).isEqualTo("patchTestAddress");

        assertThat(userRepository.findByIdOrElseThrow(saveUser1.getId()).getName()).isEqualTo("patchTestName");
    }

    @Test
    void 비밀번호수정_예외() {
        UserResponseDto saveUser1 = userService.createUser(new UserRequestDto("testName", "test@email.com", "Alsqja@0000", "testAddress", UserRole.USER));
        UserResponseDto saveUser2 = userService.createUser(new UserRequestDto("testName", "test1@email.com", "Alsqja@0000", "testAddress", UserRole.USER));
        User user1 = userRepository.findByIdOrElseThrow(saveUser1.getId());
        User user2 = userRepository.findByIdOrElseThrow(saveUser2.getId());

        PasswordUpdateRequestDto dto = new PasswordUpdateRequestDto("Alsqja@0000", "new@0000");
        PasswordUpdateRequestDto WrongPasswordDto = new PasswordUpdateRequestDto("alsqja@0000", "new@0000");

        userService.updatePassword(saveUser1.getId(), dto, user1);

        User updatedUser = userRepository.findByIdOrElseThrow(saveUser1.getId());

        PasswordEncoder passwordEncoder = new PasswordEncoder();

        ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> userService.updatePassword(saveUser2.getId(), WrongPasswordDto, user2));

        assertThat(passwordEncoder.matches("new@0000", updatedUser.getPassword())).isEqualTo(true);
        assertThat(e.getReason()).isEqualTo("비밀번호가 일치하지 않습니다.");
    }
}
