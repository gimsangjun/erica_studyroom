package com.example.demo.filter;

import com.example.demo.constants.AuthConstants;
import com.example.demo.domain.MyUserDetails;
import com.example.demo.service.JwtUserDetailsService;
import com.example.demo.util.JwtTokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
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

        String username = null;
        String jwtToken = null;

        log.info("요청 URL={}",request.getRequestURL());

        // "Authorization" 헤더가 있고, 헤더 Value가 Bearer로 시작하면
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")){
            // Value : "Bearer 토큰값"에서 Bearer를 자르고 토큰값만
            jwtToken = JwtTokenUtils.getTokenFromHeader(requestTokenHeader);
            // TODO : throw가 없는데 error가 발생하는가? -> 원래 참고했던 사이트는 어떻게? https://mangkyu.tistory.com/55
            try{
                username = jwtTokenUtils.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e){
                log.info("Unable to get JWT Token");
            } catch (ExpiredJwtException e){
                log.info("JWT Token has expired");
            }
        } else{
            log.error("JWT Token does not begin with Bearer String");
        }

        // 토큰을 받으면 유효성 확인
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){ // email이 있고, SecurityContextHolder에 인증객체가 없을 때
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
