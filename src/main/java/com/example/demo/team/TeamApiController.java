package com.example.demo.team;

import com.example.demo.ResponseDto;
import com.example.demo.user.User;
import com.example.demo.user.UserService;
import com.example.demo.user.userDetail.UserDetail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/team")
public class TeamApiController {

    private final TeamRepository teamRepository;
    private final TeamService teamService;
    public List<TeamSaveForm> TeamList = new ArrayList<>();


    private final UserService userService;

    @GetMapping("/all")
    public ResponseDto<List> teamAllInfo() {
        List<Team> all = teamRepository.findAll();
//        for (int i = 0; i < all.size(); i++) {
//            all.get(i).setId(i+1L);
//        }
        List<TeamDto> teamDtos = new ArrayList<>();
        for (Team team : all) {
            teamDtos.add(new TeamDto(team.getTeamName(), new User().userToDto(team.getLeader()), team.getIntroduce(), team.getNotice(), team.getOnAir()));
        }

        if (all.isEmpty()) return new ResponseDto<>(HttpStatus.NO_CONTENT.value(),teamDtos);
        else return new ResponseDto<>(HttpStatus.OK.value(), teamDtos);
    }

    @GetMapping("/{teamName}")
    public ResponseDto<TeamDto> teamFindByTeamName(@PathVariable String teamName) {

        Team findTeam = teamRepository.findByTeamName(teamName)
                .orElseGet(() -> {
                    return new Team();
                });


        if (findTeam.getTeamName() == null) {
            return new ResponseDto<>(HttpStatus.NO_CONTENT.value(), null);
        }
        TeamDto teamDto = teamToDto(findTeam);
        return new ResponseDto<>(HttpStatus.OK.value(), teamDto);
    }

    @GetMapping("refreshCount/onAirCount")
    public Map<Object, Object> refreshAndonAirCountView() {
        Map<Object, Object> returnObject = new ConcurrentHashMap<>();
        returnObject.put("refreshCount", TeamService.refreshCount);
        returnObject.put("Refresh TimeStamp", TeamService.refreshTimestamp);
        returnObject.put("onAirCount", TeamService.onAirCount);
        returnObject.put("onAir TimeStamp", TeamService.onAirTimestamp);
        return returnObject;
    }

    @PostMapping
    public ResponseDto<TeamSaveForm> save(@Validated @RequestBody TeamSaveForm team) {
        teamService.save(team);
        return new ResponseDto<>(HttpStatus.OK.value(), team);
    }

    @PostMapping("/onAir")
    public ResponseDto onAir(@RequestBody Map<String, String> teamNameJson) {
        String teamName = teamNameJson.get("teamName");
        return new ResponseDto<Integer>(HttpStatus.OK.value(), teamService.onAir(teamName));
    }

    @DeleteMapping("/all")
    public int delete() {
        return teamService.deleteTestDataAfter();
    }

    public TeamDto teamToDto(Team team) {
        UserDetail userDetail = team.getLeader().getUserDetail();
        if (userDetail == null) {
            return new TeamDto(team.getTeamName(), new User().userToDto(team.getLeader()), team.getIntroduce(), team.getNotice(), team.getOnAir());
        }
        return new TeamDto(team.getTeamName(), new User().userToDto(team.getLeader()), team.getIntroduce(), team.getNotice(), team.getOnAir());
    }

    @PostConstruct
    public void init() {

        /**
         *  Team Test Data
         */
        TeamList.add(new TeamSaveForm("1번팀",  "아이유","안녕하세요 1번팀입니다."));
        TeamList.add(new TeamSaveForm("2번팀",  "헤이즈","안녕하세요 2번팀입니다."));
        TeamList.add(new TeamSaveForm("3번팀",  "한서희","안녕하세요 3번팀입니다."));
        TeamList.add(new TeamSaveForm("4번팀", "디폴트사용자", "안녕하세요 4번팀입니다."));

        for (TeamSaveForm testTeam : TeamList) {
            Team Check = teamRepository.findByTeamName(testTeam.getTeamName()).orElseGet(() -> {
                return new Team();
            });
            if (Check.getTeamName() == null) {

                teamService.save(testTeam);
                userService.joinTeam(testTeam.getLeaderName(), testTeam);
                log.info("새 팀 생성");
            }
            else log.info("이미 팀이 생성 되어 있습니다.");
        }

    }

}
