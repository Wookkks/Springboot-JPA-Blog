package com.cos.blog.config.auth;

import com.cos.blog.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를
// 스프링 시큐리티의 고유한 세션 저장소에 저장을 해줌
public class PrincipalDetail implements UserDetails {

    private User user;  // 콤포지션 (객체를 품고있다)

    public PrincipalDetail(User user) {
        this.user = user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정이 만료되지 않았는지 리턴한다 (true : 만료안됨)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겨있는지 리턴한다 (true : 안잠김)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호가 만료되지 않았는지 리턴한다 (true : 만료안됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화(사용가능)가 되었는지 리턴한다 (true : 활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }

    // 계정의 권한을 리턴 (권한이 여러개 있을 수 있어서 루프를 돌아야 하는데 여기서는 한개만)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ROLE_" + user.getRole(); // 앞에 "ROLE"은 규칙이라 꼭 넣어주기 -> "ROLL_USER"
            }
        });

        // 람다식
        // 어차피 파라미터 안에 들어오는 함수는 1가지이니까
        // collectors.add(()->{ return "ROLE_" + user.getRole() });

        return collectors;
    }
}
