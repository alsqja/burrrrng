package com.example.burrrrng.service;

import com.example.burrrrng.config.PasswordEncoder;
import com.example.burrrrng.dto.PasswordUpdateRequestDto;
import com.example.burrrrng.dto.UserUpdateRequestDto;
import com.example.burrrrng.repository.UserRepository;
import com.example.burrrrng.dto.LoginRequestDto;
import com.example.burrrrng.dto.UserRequestDto;
import com.example.burrrrng.dto.UserResponseDto;
import com.example.burrrrng.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        Optional<User> user = userRepository.findByEmail(userRequestDto.getEmail());
        if (user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일 입니다.");
        }
        User savedUser = userRepository.save(userRequestDto.toEntity());
        return UserResponseDto.toDto(savedUser);
    }


    public User loginUser(LoginRequestDto loginRequestDto) {

        User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 이메일입니다."));

        PasswordEncoder passwordEncoder = new PasswordEncoder();
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "잘못된 비밀번호입니다.");
        }
        return user;
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "없는 사용자입니다."));
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UserUpdateRequestDto userUpdateRequestDto, User loginUser) {

        if (!id.equals(loginUser.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "본인만 수정이 가능합니다.");
        }

        User user = userRepository.findByIdOrElseThrow(id);

        if (userUpdateRequestDto.getName() != null && !userUpdateRequestDto.getName().isEmpty()) {
            user.updateName(userUpdateRequestDto.getName());
        }
        if (userUpdateRequestDto.getAddress() != null && !userUpdateRequestDto.getAddress().isEmpty()) {
            user.updateAddress(userUpdateRequestDto.getAddress());
        }

        return UserResponseDto.toDto(user);
    }

//    public void deleteUser(Long id) {
//        userRepository.findByIdOrElseThrow(id);
//        userRepository.deleteById(id);
//    }

    @Transactional
    public void updatePassword(Long id, PasswordUpdateRequestDto passwordUpdateRequestDto, User loginUser) {

        if (!id.equals(loginUser.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "본인만 수정이 가능합니다.");
        }

        User user = userRepository.findByIdOrElseThrow(id);

        PasswordEncoder passwordEncoder = new PasswordEncoder();
        if (!passwordEncoder.matches(passwordUpdateRequestDto.getOldPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        String newPassword = passwordEncoder.encode(passwordUpdateRequestDto.getNewPassword());
        user.updatePassword(newPassword);   // throw SQLException -> 500 INTERNAL_SERVER_ERROR
    }

    public void checkPassword(Long id, String inputPassword, User loginUser) {
        //  1. LOGINUSER 의 ID와 ID가 일치하는지 확인 -> 일치X -> THROW
        if (!id.equals(loginUser.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "본인만 확인 가능합니다.");
        }

        //  2. INPUTPASSWORD 가 DB에서 가져운 유저의 PASSWORD와 일치(MATCHS) 확인 -> 일치X -> THROW
        User user = userRepository.findByIdOrElseThrow(id);

        PasswordEncoder passwordEncoder = new PasswordEncoder();
        if(!passwordEncoder.matches(inputPassword, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

    }
}
