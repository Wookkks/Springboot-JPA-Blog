package com.cos.blog.test;

import org.springframework.web.bind.annotation.*;

// 사용자가 요청 -> 응답(HTML파일)
// @Controller

//사용자가 요청 -> 응답(Data)
// @RestController

@RestController
public class HttpControllerTest {

    private static final String TAG = "HttpControllerTest : ";

    // http://localhost:8000/blog/http/lombok
    @GetMapping("/http/lombok")
    public String lombokTest() {
        Member m = Member.builder().username("seo").password("1234").email("seo@naver.com").build();
        System.out.println(TAG + "getter : " + m.getUsername());
        m.setUsername("seoSecond");
        System.out.println(TAG + "setter : " + m.getUsername());
        return "lombok test 완료";
    }

    @GetMapping("/http/get")
    public String getTest(Member m){

        return "get 요청 : " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + "," + m.getEmail();
    }

    @PostMapping("http/post")
    public String postTest(@RequestBody Member m){
        return"post 요청 : "  + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + "," + m.getEmail();
    }

    @PutMapping("http/put")
    public String putTest(@RequestBody Member m){
        return "put 요청 : " + m.getId()+ ", " + m.getUsername() + ", " + m.getPassword() + "," + m.getEmail();
    }

    @DeleteMapping("/http/delete")
    public String deleteTest(){
        return "delete 요청";
    }

}
