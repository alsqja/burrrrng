package com.example.burrrrng.service;

import com.example.burrrrng.config.PasswordEncoder;
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
            user.setName(userUpdateRequestDto.getName());
        }
        if (userUpdateRequestDto.getAddress() != null && !userUpdateRequestDto.getAddress().isEmpty()) {
            user.setAddress(userUpdateRequestDto.getAddress());
        }

        return UserResponseDto.toDto(user);
    }

//    public void deleteUser(Long id) {
//        userRepository.findByIdOrElseThrow(id);
//        userRepository.deleteById(id);
//    }

//    @Transactional
//    public void updatePassword(Long id, UserRequestDto userRequestDto, User loginUser) {
//
//        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "없는 사용자입니다."));
//
//        PasswordEncoder passwordEncoder = new PasswordEncoder();
//        if (!passwordEncoder.matches(userRequestDto.getCurrentPassword(), user.getPassword())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
//        }
//
//        String newPassword = passwordEncoder.encode(userRequestDto.getNewPassword());
//        user.setPassword(newPassword);
//        userRepository.save(user);
//    }
}
