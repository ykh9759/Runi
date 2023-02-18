package com.example.runi.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encodePassword() {  // 회원가입 시 비밀번호 암호화에 사용할 Encoder 빈 등록
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .and()
            .authorizeRequests()
            // login 없이 접근 허용 하는 url
            .antMatchers("/", "/user/**", "/admin/signup","/admin/logout").permitAll()
            // '/admin'의 경우 ADMIN 권한이 있는 사용자만 접근이 가능
            .antMatchers("/admin/**").hasRole("ADMIN")
            // 그 외 모든 요청은 인증과정 필요
            .anyRequest().authenticated();

        http.formLogin()
            .loginPage("/admin/login")                      //로그인페이지
            .usernameParameter("id")                //아이디
            .passwordParameter("password")          //비빌번호
            .loginProcessingUrl("/admin/login")    //로그인 Action요청 
            .successHandler(                                          //로그인성공처리
                new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        System.out.println("authentication : " + authentication.getName());
                        Alert.alertAndMovePage(response, "안녕하세요." + authentication.getName() + "님", "/admin");
                        // response.sendRedirect("/admin"); // 인증이 성공한 후에는 관리자페이지 이동
                    }
                }
            )
            .failureHandler(                                        //로그인실패처리
                new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        System.out.println("exception : " + exception.getMessage());
                        Alert.alertAndMovePage(response, "로그인 실패!", "/admin/login");
                        // response.sendRedirect("/admin/login");
                    }
                }
            )
            .permitAll();                           //로그인은 모두 접근 가능

        http.csrf().disable()
            .logout()
            .logoutUrl("/admin/logout")
            .addLogoutHandler((request, response, authentication) -> { 
                HttpSession session = request.getSession();
                if (session != null) {
                    session.invalidate();
                }
            }) 
            .logoutSuccessHandler((request, response, authentication) -> {
                response.sendRedirect("/admin");
            });
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 정적인 파일 요청에 대해 무시
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
