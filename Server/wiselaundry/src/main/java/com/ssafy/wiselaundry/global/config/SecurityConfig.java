package com.ssafy.wiselaundry.global.config;

import com.ssafy.wiselaundry.domain.laundry.service.LaundryServiceImpl;
import com.ssafy.wiselaundry.domain.user.service.UserService;
import com.ssafy.wiselaundry.global.auth.JwtAuthenticationFilter;
import com.ssafy.wiselaundry.global.auth.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 인증(authentication) 와 인가(authorization) 처리를 위한 스프링 시큐리티 설정 정의.
 */
@Configuration
@EnableWebSecurity    // @EnableWebSecurity 어노테이션을 달면SpringSecurityFilterChain이 자동으로 포함
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //접근 주체(Principal) 보호된 리소스에 접근하는 유저
    //인증(Authentication) 현재 유저가 누구인지 확인, 앱 작업을 수행할 수있는 주체임을 확인
    //인가(Authorization)  현재 유저가 리소스에 접근할 수 있는 권한이 있는지 검사하는 과정
    //권한                 인증된 주체가 앱의 리소스를 이용할 수 있는 지 결정하는 요소

    @Autowired
    private UserService userService;

    @Autowired
    private LaundryServiceImpl laundryService;

    @Autowired
    private UserDetailService ssafyUserDetailService;

    // Password 인코딩 방식에 BCrypt 암호화 방식 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // DAO 기반으로 Authentication Provider를 생성
    // BCrypt Password Encoder와 UserDetailService 구현체를 설정
    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        if(ssafyUserDetailService != null){
            daoAuthenticationProvider.setUserDetailsService(this.ssafyUserDetailService);
        }
        return daoAuthenticationProvider;
    }

    // DAO 기반의 Authentication Provider가 적용되도록 설정
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override   //HttpSecurity를 통해 HTTP 요청에 대한 웹기반 보안 구성
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable() //csrf(위조 사이트요청_보호기능을 disable, http Basic 사용하기 때문)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // 토큰 기반 인증이므로 세션 사용 하지않음
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(),userService)) //HTTP 요청에 JWT 토큰 인증 필터를 거치도록 필터를 추가
                .authorizeRequests()                                                         //요청에 의한 보안검사 시작
                .antMatchers("/api/user/login").permitAll() // 로그인 허용
                .antMatchers("/api/user/signup").permitAll() // 회원가입 허용
                .antMatchers("/api/user/emailcheck").permitAll() // 회원가입 허용
                .antMatchers("/api/user/nickcheck").permitAll() // 회원가입 허용
                .antMatchers("/api/oauth/login").permitAll()// 카카오 허용
                .antMatchers("/api/weather/**").permitAll()// 날씨 허용
                .antMatchers("/api/laundry/carelabel").permitAll() //케어라벨 허용
                .antMatchers("/api/**").authenticated()  // api 로 시작하는 URL 모두 JWT 필요
//                .anyRequest().permitAll() // Swagger사용을 위해 모든 URL 허용
                .and().cors();
    }
}
