package com.example.demo.controller;

import com.example.demo.SessionConst;
import com.example.demo.dto.LoginUser;
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
public class LoginUserController {
    
    private final LoginUserService loginUserService;

    @GetMapping("/login")
    public String loginGet(Model model){ // 또는 LoginUser loginUser를 하면 아래 한줄을 줄일수 있는듯?
        model.addAttribute("loginUser",new LoginUser());
        return "login_form";
    }

    // Form을 따로만들어야됨.parameter LoginUser->LoginForm
    @PostMapping("/login")
    public String loginPost(@Validated @ModelAttribute LoginUser loginUser, BindingResult bindingResult, HttpServletRequest request){
        if(bindingResult.hasErrors()){
            log.info("LoginUser Create Error={}",bindingResult);
            return "login_form";
        }
        // user -> loginUser
        LoginUser user = this.loginUserService.create(loginUser.getLoginId(),loginUser.getPassword());
        log.info("login? {}",user);
        if (loginUser == null){
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");
            return "login_form";
        }

        // 로그인 성공처리

        HttpSession session = request.getSession();
        // user -> loginUser
        session.setAttribute(SessionConst.LOGIN_USER,user);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request) {
        //세션을 삭제한다.
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/signUp")
    public String signUpGet(Model model){
        model.addAttribute("loginUser",new LoginUser());
        return "signUp_form";
    }

    @PostMapping("/signUp")
    public String signUpPost(@Validated @ModelAttribute LoginUser loginUser, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("LoginUser Create Error={}",bindingResult);
            return "signUp_form";
        }
        log.info("LoginID={}",loginUser.getLoginId());
        log.info("Password={}",loginUser.getPassword());
        this.loginUserService.create(loginUser.getLoginId(),loginUser.getPassword());
        return "redirect:/studyRoom/list";
    }

    // userList보여주는 임시 사이트 나중에 개발.
//    @GetMapping("/user")
//    @ResponseBody
//    public String userList(){
//
//    }
}
