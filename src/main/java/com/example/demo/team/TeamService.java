package com.example.demo.team;


import com.example.demo.user.User;
import com.example.demo.user.UserDto;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    public static int refreshCount=0;
    public static Map<String, Timestamp> refreshTimestamp = new ConcurrentHashMap<>();
    public static Map<String, Timestamp> onAirTimestamp  = new ConcurrentHashMap<>();
    public static int onAirCount=0;


    @Transactional(readOnly = true)
    public ResponseEntity<List<TeamDto>> getAll() {
        List<Team> all = teamRepository.findAll();
        if (all.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        
        List<TeamDto> dtos = all.stream().map(team ->
                {
                    User leader = team.getLeader();
                    List<UserDto> userList = team.getUserList().stream()
                            .map(user -> new UserDto(
                                    user.getNickname(), user.getBirthday(), user.getGender(),
                                    user.userToDto().getUserDetail()))
                            .collect(Collectors.toList());
                    return new TeamDto(
                            team.getTeamName(), new UserDto(leader.getNickname(), leader.getBirthday(), leader.getGender(), leader.userToDto().getUserDetail()), team.getIntroduce(),
                            team.getNotice(), team.getOnAir(), team.getOnAirURL(),
                            team.getTeamProfileImg(), userList);
                }
        ).collect(Collectors.toList());
        
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity findByName(String teamName) {
        Optional<Team> team = teamRepository.findByTeamName(teamName);
        if (team.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(team);

    }
    
    @Transactional
    public boolean onAir(String teamName) {
        Team findTeam = teamRepository.findByTeamName(teamName)
                .orElseThrow(() -> {
                    return new IllegalArgumentException("error! 팀이 없습니다");
                });
        if (findTeam.getOnAir()==null || findTeam.getOnAir()==false ) {
            findTeam.setOnAir(true);
        }else findTeam.setOnAir(false);
        onAirCount++;
        onAirTimestamp.put(onAirCount + "번째 요청 Team: "+teamName+"/ onAir Time:", Timestamp.valueOf(LocalDateTime.now()));
        log.info("{} Team On Air! / onAir count={} / Time= {}", teamName,onAirCount,Timestamp.valueOf(LocalDateTime.now()));
        return findTeam.getOnAir();
    }
    @Transactional
    public int deleteTestDataAfter() {

        for (Long i =  teamRepository.findFirstByOrderByIdDesc().get().getId(); i > 4; i--) {

            Team team = teamRepository.findById(i).orElseGet(() -> {
                return new Team();
            });
            if (team.getTeamName()==null) continue;
            team.getLeader().setTeam(null);
            for (User user : team.getUserList()) {
                user.setTeam(null);
            }
            teamRepository.delete(team);
            log.info("{} 번 Team이 삭제되었습니다.",i);
        }
        refreshCount ++;
        refreshTimestamp.put(refreshCount + "번째 refresh", Timestamp.valueOf(LocalDateTime.now()));
        log.info(" Data refresh! / Refresh count: {} / Time= {}",refreshCount,Timestamp.valueOf(LocalDateTime.now()));
        return HttpStatus.OK.value();
    }
    @Transactional
    public void save(TeamSaveForm team) {

        User user = userRepository.findByNickname(team.getLeaderName()).orElseThrow(() -> {
            return new IllegalArgumentException("존재하지 않는 유저입니다.");
        });
        List<String> usernameList = team.getUserList();
        List<User> userSaveList = new ArrayList<>();

        if (usernameList != null) {
            for (String s : usernameList) {
                userSaveList.add(userRepository.findByNickname(s).orElseThrow(() -> {
                    return new IllegalArgumentException("찾으시는 유저가 없습니다.");
                }));
            }
        } else userSaveList = null;

        Team saveTeam = new Team(team.getTeamName(), user, team.getIntroduce(), team.getTeamProfileImg(), userSaveList);
        teamRepository.save(saveTeam);
        if (userSaveList != null) {
            for (User user1 : userSaveList) {
                user1.setTeam(saveTeam);
            }
        }
        user.setTeam(saveTeam);

        log.info("Create Team ={}" ,team);
    }

    @Transactional
    public void deleteTeam(TeamSaveForm teamSaveForm) {

        Team team = teamRepository.findByTeamNameAndLeader(teamSaveForm.getTeamName(), teamSaveForm.getLeaderName()).orElseThrow(() -> {
            return new IllegalArgumentException("팀이 없습니다.");

        });
        team.getLeader().setTeam(null);
        for (User user : team.getUserList()) {
            user.setTeam(null);
        }
        teamRepository.delete(team);
        log.info(" {} Team이 삭제되었습니다.", teamSaveForm.getTeamName());
    }

    @Transactional
    public void edit(String teamName, TeamSaveForm teamSaveForm) {
        Team team = teamRepository.findByTeamName(teamName).orElseThrow(()->{
            return new IllegalArgumentException("찾으시는 팀이 없습니다.");
        });

        team.setTeamName(teamSaveForm.getTeamName());
        team.setIntroduce(teamSaveForm.getIntroduce());
        team.setTeamProfileImg(teamSaveForm.getTeamProfileImg());

        log.info("Edit Teat {} -> Team: {}", teamName, teamSaveForm);
    }
}
