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
    public ResponseDto<List> teamAllInfo() {
        List<Team> all = teamRepository.findAll();
//        for (int i = 0; i < all.size(); i++) {
//            all.get(i).setId(i+1L);
//        }
        List<TeamDto> teamDtos = new ArrayList<>();
        for (Team team : all) {
            teamDtos.add(teamToDto(team));
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

    @PutMapping("/{teamName}")
    public ResponseDto<TeamSaveForm> teamEdit(@PathVariable String teamName, @RequestBody TeamSaveForm teamSaveForm) {
        teamService.edit(teamName,teamSaveForm);

        return new ResponseDto<>(HttpStatus.OK.value(), teamSaveForm);
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
        return new ResponseDto<Boolean >(HttpStatus.OK.value(), teamService.onAir(teamName));
    }

    @DeleteMapping
    public ResponseDto delete(@RequestBody TeamSaveForm team) {
        teamService.deleteTeam(team);
        return new ResponseDto<String>(HttpStatus.OK.value(), team.getTeamName() + "이 삭제 되었습니다.");
    }

    @DeleteMapping("/all")
    public int deleteAll() {
        teamService.deleteTestDataAfter();
        return 200;
    }

    public TeamDto teamToDto(Team team) {
        UserDetail userDetail = team.getLeader().getUserDetail();
        List<UserDto> userList = new ArrayList<>();
        if (team.getUserList() != null) {
            for (User user : team.getUserList()) {
                userList.add(new User().userToDto(user));
            }
        }
        if (userDetail == null) {
            return new TeamDto(team.getTeamName(), new User().userToDto(team.getLeader()), team.getIntroduce(), team.getNotice(), team.getOnAir(), team.getOnAirURL(),team.getTeamProfileImg(),userList);
        }
        return new TeamDto(team.getTeamName(), new User().userToDto(team.getLeader()), team.getIntroduce(), team.getNotice(), team.getOnAir(), team.getOnAirURL(), team.getTeamProfileImg(),userList);
    }

    public void roomTestData(TeamSaveForm onAirTeam) {

        if (onAirTeam.getTeamName() == "1번팀") {
            System.out.println(1);
            roomService.createRoom(new RoomSaveDto(onAirTeam.getTeamName() + "의 방", onAirTeam.getTeamName(), "안녕하세요 " + onAirTeam.getTeamName() + "의 방입니다.", "37.497535461501111, 127.02948149502222"));
        }
        if (onAirTeam.getTeamName() == "2번팀") {
            System.out.println(2);
            roomService.createRoom(new RoomSaveDto(onAirTeam.getTeamName() + "의 방", onAirTeam.getTeamName(), "안녕하세요 " + onAirTeam.getTeamName() + "의 방입니다.", "37.497535461503333, 127.02948149504444"));
        }
        if  (onAirTeam.getTeamName()=="3번팀") return;
        if (onAirTeam.getTeamName() == "4번팀") {
            System.out.println(4);
            roomService.createRoom(new RoomSaveDto(onAirTeam.getTeamName() + "의 방", onAirTeam.getTeamName(), "안녕하세요 " + onAirTeam.getTeamName() + "의 방입니다.", "37.497535461505555, 127.02948149506666"));
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
