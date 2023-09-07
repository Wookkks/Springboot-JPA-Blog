package com.cos.blog.repository;

import com.cos.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// DAO (Data Access Object)
// 자동으로 Bean으로 등록이 된다.
// @Repository 생략 가능!
public interface UserRepository extends JpaRepository<User, Integer> {


}

// JPA Naming 전략
// SELECT * FROM user WHERE username = ? AND password = ?;
//    User findByUsernameAndPassword(String username, String password);

//    @Query(value = "SELECT * FROM user WHERE username = ? AND password = ?", nativeQuery = true)
//    User login(String username, String password);