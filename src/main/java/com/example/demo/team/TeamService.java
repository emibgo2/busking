package com.example.demo.team;


import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    @Transactional
    public Integer onAir(String teamName) {
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
        return 200;
    }
    @Transactional
    public int deleteTestDataAfter() {
        for (Long i =  teamRepository.count(); i > 4; i--) {
            teamRepository.deleteById(i);
            log.info("{} 번 user가 삭제되었습니다.",i);
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
        teamRepository.save(new Team(team.getTeamName(), user, team.getIntroduce()));
        log.info("Create Team ={}" ,team);
    }
}
