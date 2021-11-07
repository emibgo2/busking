package com.example.demo.team;

import com.example.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team,Long> {
    // SELECT * FROM user WHERE loginID = 1?;
    Optional<Team> findByTeamName(String username);

    void deleteByTeamName(String teamName);

//
//    Optional<User> deleteUserByNickName(String nickname);
}
