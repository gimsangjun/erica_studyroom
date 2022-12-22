package com.example.demo.test.controller;

import com.example.demo.test.request.LoginRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller()
public class LoginController {

    @GetMapping("/test/login")
    public String loginForm(Model model){

        model.addAttribute("member",new LoginRequest());
        return "/test/login";
    }
}
