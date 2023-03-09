package com.example.demo.config.security;

import com.example.demo.enums.exception.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@Slf4j
// 인증과정에서 실패하거나 인증헤더(Authorization)를 보내지 않게되는 경우 401(UnAuthorized) 라는 응답값을 받게되는데 그것을 처리하는 인터페이스
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Integer exception = (Integer) request.getAttribute("exception");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        PrintWriter writer = response.getWriter();
        if(exception == null){
            writer.println("HTTP Status 401 UNAUTHORIZED - " + authException.getMessage());
        }
        else if(exception == JwtException.WRONG_TOKEN.getCode()){
            writer.println("HTTP Status 401 UNAUTHORIZED - " + JwtException.WRONG_TOKEN.getMessage());
        }
        else if(exception == JwtException.EXPIRED_TOKEN.getCode()){
            writer.println("HTTP Status 401 UNAUTHORIZED - " + JwtException.EXPIRED_TOKEN.getMessage());
        }
        else if(exception == JwtException.SIGNATURE_ERROR.getCode()){
            writer.println("HTTP Status 401 UNAUTHORIZED - " + JwtException.SIGNATURE_ERROR.getMessage());
        }
        else {
            log.warn("Nothing catching");
        }
    }
}
