package com.example.demo.user.controller;

import com.example.demo.user.domain.User;
import com.example.demo.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    private static final String LOGIN_USER = "LOGIN_USER";

    // 회원가입 화면 표시
    @GetMapping("/signup")
    public String showSignupForm() {
        return "user/signup";
    }

    // 회원가입 (사용자 입력 데이터)
    @PostMapping("/signup")
    public String doSignup(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String nickname) {
        try {
            userService.register(username, password, nickname);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            return "user/signup";
        }
    }

    // 로그인 화면 표시
    @GetMapping("/login")
    public String showLogin() {
        return "user/login";
    }

}
