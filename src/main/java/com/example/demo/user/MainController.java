package com.example.demo.user;

import com.example.demo.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;

    @GetMapping("/")
    public String main(HttpSession session, Model model) {

        String nickname = (String)session.getAttribute("LOGIN_NICKNAME");
        model.addAttribute("nickname", nickname);

        return "main";
    }
}
