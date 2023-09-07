package com.cos.blog.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Bean 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것
@Configuration  // Bean 등록 (IoC 관리)
@EnableWebSecurity  // 시큐리티 필터가 등록이 된다 = 스프링 시큐리티가 활성화가 되어 있는데 설정을 이 파일에서 하겠다
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다
public class SecurityConfig {

    @Bean
    BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                // csrf 토큰 비활성화 (테스트시 걸어두는 게 좋음)
                .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/", "/auth/**", "/js/**", "/css/**", "/img/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                .and()
                    .formLogin()
                    .loginPage("/auth/loginForm");

        return http.build();
    }
}
