package com.cos.blog.test;

import lombok.*;

// @RequiredArgsConstructor // final 붙은 변수에 대한 생성자를 만들어줌
@Data
@NoArgsConstructor
public class Member {
    private int id;
    private String username;
    private String password;
    private String email;

    @Builder
    public Member(int id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }
}


