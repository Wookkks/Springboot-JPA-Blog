package com.cos.blog.config;


import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.config.auth.PrincipalDetailService;
import com.cos.blog.model.RoleType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

// Bean 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것
@Configuration  // Bean 등록 (IoC 관리)
@EnableWebSecurity  // 시큐리티 필터가 등록이 된다 = 스프링 시큐리티가 활성화가 되어 있는데 설정을 이 파일에서 하겠다
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다
public class SecurityConfig {
    @Bean
    BCryptPasswordEncoder encodePWD() {
        return new BCryptPasswordEncoder();
    }
    private final PrincipalDetailService principalDetailService;
    public SecurityConfig(PrincipalDetailService principalDetailService) {
        this.principalDetailService = principalDetailService;
    }

    // 시큐리티가 대신 로그인 하면서 password를 가로채는데
    // 해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
    // 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교를 할 수 있음
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                // csrf 토큰 비활성화 (테스트시 걸어두는 게 좋음)
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/", "/auth/**", "/js/**", "/css/**", "/img/**", "/dummy/**", "/test/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                .and()
                    .formLogin()
                    .loginPage("/auth/loginForm")
                    .loginProcessingUrl("/auth/loginProc") // 스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인을 해줌
                    .defaultSuccessUrl("/");

        return http.build();
    }
}
