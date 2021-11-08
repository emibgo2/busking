package com.example.demo.user;

import com.example.demo.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // SELECT * FROM user WHERE loginID = 1?;
    Optional<User> findByUsername(String username);
    Optional<User> deleteUserByUsername(String username);
    Optional<User> findByNickname(String nickName);
    Optional<User> findFirstByOrderByIdDesc();
    Optional<List<User>> findByNicknameContains(String nickName);
    // SELECT * FROM user WHERE loginID = 1?;
    // findBy = SELECT * FROM user <- Optinal findBy/Username -> WHERE loginID
//    Optional<User> findByUsername(String loginID);
    //JPA Naming 전략
// SELECT * FROM user WHERE loginID= ? AND password = ?;
//    User findByUsernameAndPassword(String loginID, String password);

//    @Query(value = "SELECT * FROM user WHERE loginID= ?1 AND password = ?2", nativeQuery = true)
//    User login(String loginID, String password);
}
