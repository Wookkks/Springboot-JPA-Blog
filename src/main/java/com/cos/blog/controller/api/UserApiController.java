package com.cos.blog.controller.api;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UserApiController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public UserApiController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user) { // username, password, email
        // 실제로 DB에 insert를 하고 아래에서 return이 되면 됨
        userService.회원가입(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 자바오브젝트를 JSON으로 변환해서 리턴 (Jackson 라이브러리)
    }

    @PutMapping("/user")
    public ResponseDto<Integer> update(@RequestBody User user) { // @RequestBody : Json으로 받기위해서
        userService.회원수정(user);
        // 여기서는 트랜잭션이 종료되기 때문에 DB의 값은 변경이 됐음
        // 하지만 세션값은 변경되지 않은 상태이기 때문에 직접 세션값을 변경해줄 것

        // 세션등록
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

//    @PostMapping("/api/user/login")
//    public ResponseDto<Integer> login(@RequestBody User user, HttpSession session) {
//        System.out.println("UserApiController : login 호출됨");
//        User principal = userService.로그인(user); // principal (접근주체)
//
//        if(principal != null) {
//            session.setAttribute("principal", principal); // 세션 생성
//        }
//
//        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
//    }
}
