package com.example.runi.config;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
            .authorizeHttpRequests()
            // login 없이 접근 허용 하는 url
            .antMatchers("/", "/user/**", "/member/signup","/member/logout","/member/loginFail").permitAll()
            // '/member'의 경우 ADMIN 권한이 있는 사용자만 접근이 가능
            .antMatchers("/member/**").hasAnyRole("ADMIN","MEMBER")
            // 그 외 모든 요청은 인증과정 필요
            .anyRequest().authenticated();

        http.formLogin()
            .loginPage("/member/login")                      //로그인페이지
            .usernameParameter("id")                //아이디
            .passwordParameter("password")          //비빌번호
            .loginProcessingUrl("/member/login")    //로그인 Action요청 
            .successHandler(                                          //로그인성공처리
                new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        System.out.println("authentication : " + authentication.getName());

                        response.sendRedirect("/member"); // 인증이 성공한 후에는 관리자페이지 이동
                    }
                }
            )
            .failureHandler(                                        //로그인실패처리
                new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        System.out.println("exception : " + exception.getMessage());

                        String errorMessage;
                        if (exception instanceof BadCredentialsException) {
                            errorMessage = "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해 주세요.";
                        }  else {
                            errorMessage = "알 수 없는 이유로 로그인에 실패하였습니다 관리자에게 문의하세요.";
                        }

                        errorMessage = URLEncoder.encode(errorMessage, "UTF-8");
                        response.sendRedirect("/member/loginFail?exception="+errorMessage);
                    }
                }
            )
            .permitAll();                           //로그인은 모두 접근 가능

        http.csrf().disable()
            .logout()
            .logoutUrl("/member/logout")
            .addLogoutHandler((request, response, authentication) -> { 
                HttpSession session = request.getSession();
                if (session != null) {
                    session.invalidate();
                }
            }) 
            .logoutSuccessHandler((request, response, authentication) -> {
                response.sendRedirect("/member");
            });
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 정적인 파일 요청에 대해 무시
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
        web.ignoring().antMatchers("/vendor/**");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
