package com.example.demo.team;

import com.example.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team,Long> {
    // SELECT * FROM user WHERE loginID = 1?;
    Optional<Team> findByTeamName(String username);

    Optional<Team> findFirstByOrderByIdDesc();

//    @Query("SELECT id FROM Team WHERE ROWNUM=1 ORDER BY id desc ")
//    Optional<Team> findByLastTeam();
    void deleteByTeamName(String teamName);

    Optional<Team>findByTeamNameAndLeader(String teamName, String leaderName);

//
//    Optional<User> deleteUserByNickName(String nickname);
}
