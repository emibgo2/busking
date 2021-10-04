package com.example.demo.team;


import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    @Transactional
    public Integer onAir(String teamName) {
        Team findTeam = teamRepository.findByTeamName(teamName)
                .orElseThrow(() -> {
                    return new IllegalArgumentException("error! 팀이 없습니다");
                });
        if (findTeam.getOnAir()==null || findTeam.getOnAir()==false ) {
            findTeam.setOnAir(true);
        }else findTeam.setOnAir(false);

        return 200;
    }
    @Transactional
    public int deleteTestDataAfter() {
        for (Long i =  teamRepository.count(); i > 4; i--) {
            teamRepository.deleteById(i);
            log.info("{} 번 user가 삭제되었습니다.",i);
        }
        return HttpStatus.OK.value();
    }
    @Transactional
    public void save(TeamSaveForm team) {
        System.out.println("team.getLeaderName() = " + team.getLeaderName());
        User user = userRepository.findByNickname(team.getLeaderName()).orElseThrow(() -> {
            return new IllegalArgumentException("존재하지 않는 유저입니다.");
        });
        teamRepository.save(new Team(team.getTeamName(), user, team.getIntroduce()));
        log.info("Create Team ={}" ,team);
    }
}
