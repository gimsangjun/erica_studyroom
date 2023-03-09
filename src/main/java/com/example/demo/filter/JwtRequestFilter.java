package com.example.demo.filter;

import com.example.demo.constants.AuthConstants;
import com.example.demo.domain.MyUserDetails;
import com.example.demo.enums.exception.JwtException;
import com.example.demo.service.JwtUserDetailsService;
import com.example.demo.util.JwtTokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUserDetailsService jwtUserDetailsService;

    private final JwtTokenUtils jwtTokenUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // Postman에서는 Key: "Authorization" , Value : "Bearer 토큰값" 임.
        final String requestTokenHeader = request.getHeader(AuthConstants.AUTH_HEADER);

        log.info("{} request URL={}",request.getMethod(),request.getRequestURL());
        log.info("인증헤더={}", requestTokenHeader);

        String username = null;
        String jwtToken = null;

        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")){
            // Value : "Bearer 토큰값"에서 Bearer를 자르고 토큰값만
            jwtToken = JwtTokenUtils.getTokenFromHeader(requestTokenHeader);
            try{ // 예외는 JwtTokenUtils::getAllClaimsFromToken 에서 던져줌.
                username = jwtTokenUtils.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                // 정확히 어떤 상횡일때 에러가 생기는지 모르겠음.
                log.error("an error occured during getting username from token", e);
                request.setAttribute("exception", JwtException.WRONG_TOKEN.getCode());
            } catch (ExpiredJwtException e) {
                log.warn("the token is expired and not valid anymore", e);
                request.setAttribute("exception", JwtException.EXPIRED_TOKEN.getCode());
            } catch (SignatureException e){
                // JWT토큰값이 이상함.
                log.error("JWT signature does not match locally computed signature.");
                request.setAttribute("exception", JwtException.SIGNATURE_ERROR.getCode());
            } // MalformedJwtException도 넣어야할듯 .JWT앞부분쪽 건드리면 발생
            // 비밀번호 틀렸는지 체크하는것은 AuthController에서 exception을 따로 던진다.
        } else{
            log.warn("couldn't find bearer string, will ignore the header");
        }

        // 토큰을 받으면 유효성 확인
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            MyUserDetails userDetails = (MyUserDetails) this.jwtUserDetailsService.loadUserByUsername(username);

            if (jwtTokenUtils.validateToken(jwtToken,userDetails)){
                // 인증 객체 생성
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 인증 정보를 저장.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }
        chain.doFilter(request,response);
    }

}
