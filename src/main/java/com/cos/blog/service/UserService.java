package com.cos.blog.service;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC를 해준다. (메모리에 대신 띄워준다)
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Transactional(readOnly = true)
    public User 회원찾기(String username) {
        User user = userRepository.findByUsername(username).orElseGet(()->{
            return new User();
        });
        return user;
    }

    @Transactional // 회원가입 전체 서비스가 하나의 Transaction으로 묶이게 됨
    public void 회원가입(User user) {
        String rawPassword = user.getPassword(); // 원문
        String encPassword = encoder.encode(rawPassword); // 해쉬
        user.setPassword(encPassword);
        user.setRole(RoleType.USER);
        userRepository.save(user);
    }

    @Transactional
    public void 회원수정(User user) {
        // 수정시에는 영속성 컨텍스트에 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정
        // SELECT를 해서 User 오브젝트를 DB로부터 가져오는 이유는 영속화를 하기 위함
        // 영속화를 하면 영속화된 오브젝트를 변경하면 자동으로 DB에 UPDATE문을 날려줌
        User persistance = userRepository.findById(user.getId()).orElseThrow(()-> {
           return new IllegalArgumentException("회원 찾기 실패");
        });

        // Validate 체크 => oauth에 값이 없으면(일반 로그인 사용자) 수정 가능
        if(persistance.getOauth() == null || persistance.getOauth().equals("")) {
            String rawPassword = user.getPassword();
            String encPwssword = encoder.encode(rawPassword);
            persistance.setPassword(encPwssword);
            persistance.setEmail(user.getEmail());
        }
        // 회원수정 함수 종료시 = 서비스 종료 = 트랜잭션 종료 = commit이 자동으로 됨
        // 영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 변화된 것들에 대한 UPDATE문을 날려줌

    }


//    @Transactional(readOnly = true) // SELECT할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료 (정합성 유지)
//    public User 로그인(User user) {
//        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//    }
}
