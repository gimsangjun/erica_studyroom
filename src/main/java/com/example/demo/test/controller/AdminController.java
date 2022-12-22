package com.example.demo.test.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/test/admin")
public class AdminController {

    @GetMapping("/home")
    public String home() {
        return "adminHome";
    }
}