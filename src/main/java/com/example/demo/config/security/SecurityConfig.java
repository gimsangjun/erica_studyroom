package com.example.demo.config.security;

import com.example.demo.filter.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@EnableWebSecurity // Spring Security  설정활성화
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // TODO: 스프링부트가 업데이트가 되면서 WebSecurityConfigurerAdapter를 deprecated되고 ,SecurityFilterChain을 Bean으로 등록해서 사용해야함.

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final UserDetailsService jwtUserDetailsService; // 이렇게 이름을 지을경우 JwtUserDetailsService를 가져오나?
    private final JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 아래의 요청들은 Spring security 로직을 수행하지 않도록 모두 무시하도록 설정.
        web
                .ignoring()
                .antMatchers(
                        "/h2-console/**"
                        ,"/favicon.ico"
                );
    }

    @Bean
    @Override // AuthenticationManager 를 외부에서 사용 하기 위해
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors() // TODO: 역할?
                .and()
                // 아래의 요청들은 토큰없이도 가능하도록
                .authorizeRequests().antMatchers("/api/user/login","/api/user/signUp","/api/user/header").permitAll()
                // 위를 제외한 다른 요청들은 인증이 필요
                .anyRequest().authenticated()
                .and()
                // exception을 핸들링하기 위함.
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                // 토큰을 활용하면 세션이 필요 없으므로 STATELESS로 설젛아여 Session을 사용하지 않는다.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // form 기반의 로그인에 대해 비활성화 한다.
                .formLogin()
                .disable()
                // 모든 리퀘스트마다 토큰을 유효성 검사하기위해 필터 추가.
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class) // TODO : CORS 적용안됨.
                ;
    }

    // https://oingdaddy.tistory.com/243
    //  @Bean // 글로벌하게 CORS 필터없이 간단하게 CORS설정하기 -> 어노테이션으로도 됐음.
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // 요청을 허용하는 출처, 와일드 카드이므로 모든곳에서 허용
        configuration.setAllowedOrigins(Arrays.asList("*"));
        // 기본은 GET과 POST,OPTIONS : Preflight Request(확인을 위한 사전 요청)
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        // 요청을 허용하는 헤더, TODO: exposedheader랑 차이를 모르겟음.
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        // 클라이언트의 요청에 포함되어도 되는 사용자의 정의 헤더
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        // 클라이언트 요청이 쿠키를 통해서 자격 증명을 하는 경우에 true,
        // true를 응답받은 클라이언트는 실제 요청 시 서버에서 정의된 규격의 인증값이 담긴 쿠키를 같이 보내야 한다.
        // true를 할경우에는 허용된 Origin은 와일드카드(*)가 안된다.
        // configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // TODO: 문제 회원가입과 로그인을 할때는 인증토큰이 필요없는데, 다른것들을 할때는 인증토큰이 필요, URL을 인증, no인증으로 나눠야하나? 좀더 효율적인 방법이 없나?
        // 아니면 이거는 글로벌적으로 하는거니까 메서드차원에서 다시 crossorign을 사용하면되나?
        source.registerCorsConfiguration("/**", configuration);

        // 토큰이 필요할때
//        CorsConfiguration configuration1 = new CorsConfiguration();
//        configuration1.setAllowedOriginPatterns(Arrays.asList("/api/user/**")); // 커스텀 어노테이션으로 줄여야할듯?


        return source;
    }

}
