package com.cos.blog;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class EncTest {

    @Test
    public void 해쉬_암호화() {
        String endPassword = new BCryptPasswordEncoder().encode("1234");
        System.out.println("1234 해쉬 : " + endPassword);
    }
}
