package com.example.demo.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    // SELECT * FROM user WHERE username = 1?;
    Optional<User> findByUsername(String username);

    // SELECT * FROM user WHERE username = 1?;
    // findBy = SELECT * FROM user <- Optinal findBy/Username -> WHERE username
//    Optional<User> findByUsername(String username);
    //JPA Naming 전략
// SELECT * FROM user WHERE username= ? AND password = ?;
//    User findByUsernameAndPassword(String username, String password);

//    @Query(value = "SELECT * FROM user WHERE username= ?1 AND password = ?2", nativeQuery = true)
//    User login(String username, String password);
}
