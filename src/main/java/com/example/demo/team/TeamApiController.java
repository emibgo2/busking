package com.example.demo.team;

import com.example.demo.ResponseDto;
import com.example.demo.room.RoomSaveDto;
import com.example.demo.room.RoomService;
import com.example.demo.user.User;
import com.example.demo.user.UserDto;
import com.example.demo.user.UserService;
import com.example.demo.user.userDetail.UserDetail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final RoomService roomService;
    public List<TeamSaveForm> TeamList = new ArrayList<>();


    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<TeamDto>> teamAllInfo() {
        return teamService.getAll();
    }

    @GetMapping("/{teamName}")
    public ResponseEntity<TeamDto> teamFindByTeamName(@PathVariable String teamName) {

        return teamService.findByName(teamName);
    }

    @PutMapping("/{teamName}")
    public ResponseEntity<TeamSaveForm> teamEdit(@PathVariable String teamName, @RequestBody TeamSaveForm teamSaveForm) {
        teamService.edit(teamName,teamSaveForm);

        return ResponseEntity.ok(teamSaveForm);
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
    public ResponseEntity<TeamSaveForm> save(@Validated @RequestBody TeamSaveForm team) {
        teamService.save(team);
        return ResponseEntity.ok(team);
    }

    @PostMapping("/onAir")
    public ResponseEntity onAir(@RequestBody Map<String, String> teamNameJson) {
        String teamName = teamNameJson.get("teamName");

        return ResponseEntity.ok(teamService.onAir(teamName));
    }

    @DeleteMapping
    public void delete(@RequestBody TeamSaveForm team) {
        teamService.deleteTeam(team);
    }

    @DeleteMapping("/all")
    public void deleteAll() {
        teamService.deleteTestDataAfter();
    }

    public void roomTestData(TeamSaveForm onAirTeam) {
        if (onAirTeam.getTeamName() == "1번팀") {
            roomService.createRoom(new RoomSaveDto(onAirTeam.getTeamName() + "의 방", onAirTeam.getTeamName(), "안녕하세요 " + onAirTeam.getTeamName() + "의 방입니다.", "37.497535461501111, 127.02948149502222"));
        }
    }

    @PostConstruct
    public void init() {

        /**
         *  Team Test Data
         */
        TeamList.add(new TeamSaveForm("1번팀",  "아이유","안녕하세요 1번팀입니다.","https://www.theguru.co.kr/data/photos/20210937/art_16316071303022_bf8378.jpg",null));
        TeamList.add(new TeamSaveForm("2번팀",  "헤이즈","안녕하세요 2번팀입니다.","https://file.mk.co.kr/meet/neds/2021/02/image_readtop_2021_188127_16142386024553959.jpg",null));
        TeamList.add(new TeamSaveForm("3번팀",  "한서희","안녕하세요 3번팀입니다.","https://i.pinimg.com/originals/c0/da/57/c0da57e76bde0ccc9fc503bb3f77d217.jpg",null));
        TeamList.add(new TeamSaveForm("4번팀", "디폴트사용자", "안녕하세요 4번팀입니다.",null,null));

        for (TeamSaveForm testTeam : TeamList) {
            Team Check = teamRepository.findByTeamName(testTeam.getTeamName()).orElseGet(() -> {
                return new Team();
            });
            if (Check.getTeamName() == null)
            {

                testTeam.setLeaderName(testTeam.getLeaderName());
                teamService.save(testTeam);

                roomTestData(testTeam);
                log.info("새 팀 생성");
            }
            else log.info("이미 팀이 생성 되어 있습니다.");
        }


    }

}
