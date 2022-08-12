package com.example.demo;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @GetMapping("/")
    public String index(Model model){

        User userA = new User("userA",10);
        User userB = new User("userB",20);

        List<User> list = new ArrayList<>();
        list.add(userA);
        list.add(userB);

        model.addAttribute("users", list);
        return "index";
    }

    @GetMapping("/layout")
    public String layout(Model model){
        return "layoutMain";
    }

    @Data
    static class User{
        private String username;
        private int age;

        public User(String username, int age){
            this.username=username;
            this.age = age;
        }
    }
}
