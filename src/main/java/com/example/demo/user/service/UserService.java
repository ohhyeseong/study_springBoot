package com.example.demo.user.service;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserRole;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(String username, String password, String nickname) {

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
    @Transactional(readOnly = true)// 이 메서드는 get(조회)메서드라 spring에게 알려준다.
    public Optional<User> getUser(String username) {
        return userRepository.findByUsername(username);
    }
    // 그럼 쓸데없이 transactional이 스냅샷을 만들어 변경감지를 하고 update 쿼리를 생성을 안하기 때문에
    // 성능이 더 좋아진다.
}
