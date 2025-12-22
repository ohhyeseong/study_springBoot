package com.example.demo.user.controller;

import com.example.demo.user.domain.User;
import com.example.demo.user.dto.UserSignupDto;
import com.example.demo.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    private static final String LOGIN_USER = "LOGIN_USER";

    // 회원가입 화면 표시
    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("userSignupDto", new UserSignupDto());
        return "user/signup";
    }

    // 회원가입 (사용자 입력 데이터)
    @PostMapping("/signup")
    public String doSignup(@Valid @ModelAttribute UserSignupDto userSignupDto,
                           BindingResult bindingResult,
                           Model model) {// bindingResult에 검증 결과가 담겨있음.
        // 1. 검증 결과를 확인(Null, "", " " 등을 체크)
        if(bindingResult.hasErrors()){
            // DTO 검증(예: @NotBlank)에 실패했다면,
            // 서비스 로직을 호출하지 않고 바로 회원가입 폼으로 이동
            return "user/signup";
        }
        // 2. 검증을 통과하면, 서비스 로직 호출
        try {
            userService.register(
                    userSignupDto.getUsername(),
                    userSignupDto.getPassword(),
                    userSignupDto.getNickname());
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage()); // 에러 메시지를 ty문법 모델에 객체에 추가
            return "user/signup";
        }
    }

    // 로그인 화면 표시
    @GetMapping("/login")
    public String showLogin() {
        return "user/login";
    }

}
