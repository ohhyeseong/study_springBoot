package com.example.demo.user.service;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserRole;
import com.example.demo.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(String username, String password, String nickname) {
        if (isBlank(username)) throw new IllegalArgumentException("아이디는 필수입니다.");
        if (isBlank(password)) throw new IllegalArgumentException("비밀번호는 필수입니다.");
        if (isBlank(nickname)) throw new IllegalArgumentException("닉네임은 필수입니다.");

        if (userRepository.existsByUsername(username)) throw new IllegalArgumentException("아이디가 이미 존재합니다.");

        String encodedPassword = passwordEncoder.encode(password);

        User user = User.builder()
                .username(username)
                .password(encodedPassword)
                .nickname(nickname)
                .role(UserRole.USER)
                .build();

        return userRepository.save(user);
    }

    // 사용자 로그인을 위한 아이디 있는지 확인 메서드
    public Optional<User> getUser(String username) {
        return userRepository.findByUsername(username);
    }



    private boolean isBlank(String s){
        return s == null || s.trim().isEmpty();
    }
}
