package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@DynamicInsert // insert 시에 null인 필드를 제외시켜준다.
@Entity
public class User {

    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
    private int id; // 시퀀스, auto_increment

    @Column(nullable = false, length = 100, unique = true)
    private String username; // 아이디

    @Column(nullable = false, length = 100) // 비밀번호 암호화 예정이라서 길게
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    //@ColumnDefault("'user'")
    // DB는 RoleType이라는게 없다
    @Enumerated(EnumType.STRING)
    private RoleType role; // Enum을 쓰는게 좋다. // ADMIN, USER

    private String oauth; // kakao, google, ...

    @CreationTimestamp // 시간이 자동으로 입력됨
    private Timestamp createDate;

}
