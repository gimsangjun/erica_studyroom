package com.example.demo.controller;

import com.example.demo.SessionConst;
import com.example.demo.dto.LoginUser;
import com.example.demo.form.LoginForm;
import com.example.demo.form.SignUpForm;
import com.example.demo.service.LoginUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@Controller
public class LoginController {
    
    private final LoginUserService loginUserService;

    @GetMapping("/login")
    public String loginGet(@ModelAttribute("loginForm") LoginForm form){
        return "login_form";
    }

    @PostMapping("/login")
    public String loginPost(@Validated @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request){
        if(bindingResult.hasErrors()){
            log.info("Login Error={}",bindingResult);
            return "login_form";
        }
        LoginUser loginUser = this.loginUserService.loadUserByLoginId(loginForm.getLoginId(),loginForm.getPassword());
        log.info("login? {}",loginUser);
        if (loginUser == null){
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");
            return "login_form";
        }

        // 로그인 성공처리
        HttpSession session = request.getSession();
        // 세션에 저장.
        session.setAttribute(SessionConst.LOGIN_USER,loginUser);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        //세션을 삭제한다.
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    // 회원가입
    @GetMapping("/signUp")
    public String signUpGet(@ModelAttribute("signUpForm") SignUpForm signUpForm){
        return "signUp_form";
    }

    @PostMapping("/signUp")
    public String signUpPost(@Validated @ModelAttribute("signUpForm") SignUpForm signUpForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("signUp Error={}",bindingResult);
            return "signUp_form";
        }
        log.info("signUp user={}",signUpForm);
        this.loginUserService.create(signUpForm.getLoginId(),signUpForm.getPassword(), signUpForm.getName());
        return "redirect:/";
    }

    // userList보여주는 임시 사이트 나중에 개발.
//    @GetMapping("/user")
//    @ResponseBody
//    public String userList(){
//
//    }
}
