package com.example.demo;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @ModelAttribute("lists")
    public List<User> users(){
        User userA = new User("userA",10);
        User userB = new User("userB",20);
        User userC = new User("userC",23);

        List<User> list = new ArrayList<>();
        list.add(userA);
        list.add(userB);
        list.add(userC);

        return list;
    }

    @GetMapping("/")
    public String index(Model model){
        return "index";
    }

    @GetMapping("/layout")
    public String layout(Model model){
        return "layoutMain";
    }

    @GetMapping("/list")
    public String list(Model model){
        return "list";
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
