package com.example.users_service.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserController{
    @GetMapping("/staff")
    public String showLandingPage(Model model) {
        return "landing_page"; // Trả về tên file Thymeleaf trong templates
    }

    @GetMapping("/personal")
    public String showPersonalPage(Model model) {

        return "personal_page"; // Trả về tên file Thymeleaf trong templates
    }
}
