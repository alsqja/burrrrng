package com.example.burrrrng.Service;

import com.example.burrrrng.Repository.UserRepository;
import com.example.burrrrng.dto.LoginRequestDto;
import com.example.burrrrng.dto.UserRequestDto;
import com.example.burrrrng.dto.UserResponseDto;
import com.example.burrrrng.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
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
//        Optional<User> user = userRepository.findByEmail(loginRequestDto.getEmail());
////        if (user == null || !Objects.equals(user.getPassword(), loginRequestDto.getPassword())) {
////            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "잘못된 비밀번호입니다.");
//        }
        return null;
    }
}
