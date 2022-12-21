package com.example.demo.room;

import com.example.demo.ResponseDto;
import com.example.demo.music.Music;
import com.example.demo.room.Rmusic.RMusic;
import com.example.demo.room.Rmusic.RMusicRepository;

import com.example.demo.team.Team;
import com.example.demo.team.TeamRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;
    private final TeamRepository teamRepository;
    private final RMusicRepository rMusicRepository;


    @Transactional(readOnly = true)
    public List<RoomDto> getAll() {
        return roomRepository.findAll().stream()
                .map(
                        room -> {
                            return RoomDto.builder()
                                    .viewer(room.getViewer().stream().map(User::userToDto).collect(Collectors.toList()))
                                    .introduce(room.getIntroduce())
                                    .musics(room.getReservationMusic())
                                    .roomName(room.getRoomName())
                                    .teamName(room.getOnAirTeam().getTeamName())
                                    .teamProfileImg(room.getOnAirTeam().getTeamProfileImg())
                                    .build();
                        }

                ).collect(Collectors.toList());
    }

    @Transactional
    public ResponseEntity<String> createRoom(RoomSaveDto room) {

        Team team = teamRepository.findByTeamName(room.getTeamName()).orElseThrow(() -> {
            return new IllegalArgumentException("해당하는 Team 이 없습니다.");
        });
        if ( team.getOnAirURL() != null) {
            return new ResponseEntity<>("이미 방송중인 팀입니다.",HttpStatus.NOT_ACCEPTABLE) ;
        }
        team.setOnAirURL("https://busking-back.herokuapp.com/room/" + room.getRoomName() + "/" + room.getTeamName());
        team.setOnAir(true);
        Room saveRoom = Room.builder()
                .roomName(room.getRoomName())
                .onAirTeam(team)
                .introduce(room.getIntroduce())
                .teamProfileImg(team.getTeamProfileImg())
                .latIng(room.getLatIng()).build();
        roomRepository.save(saveRoom);

        log.info("Create Room! Room Information: {}",room);
        return new ResponseEntity<>("https://busking-back.herokuapp.com/room/" + room.getRoomName() + "/" + room.getTeamName(), HttpStatus.OK);
    }


    @Transactional
    public ResponseEntity<RoomDto> reservationMusic(String roomName, String teamName, RMusic rMusic) {
        Room findRoom = roomRepository.findByRoomNameAndOnAirTeam_TeamName(roomName, teamName).orElseThrow(() -> {
            return new IllegalArgumentException("찾으시는 방은 존재하지 않습니다");
        });
        log.info(findRoom.getRoomName());

        RMusic reserMusic = rMusicRepository.findByMusicRoomIdAndTitleAndSinger(findRoom.getId(), rMusic.getTitle(), rMusic.getSinger()).orElseGet(() -> {
            return null;
        });
        if (reserMusic == null) {
            rMusic.setMusicRoom(findRoom);
            rMusicRepository.save(rMusic);
            findRoom.getReservationMusic().add(rMusic);
            return new ResponseEntity<>(findRoom.roomToRoomDTO(), HttpStatus.OK);
        } else return new ResponseEntity<>(findRoom.roomToRoomDTO(), HttpStatus.NOT_ACCEPTABLE);


    }

    @Transactional(readOnly = true)
    public RoomDto getByRoomNameAndTeamName(String roomName, String teamName) {
        Room findRoom = roomRepository.findByRoomNameAndOnAirTeam_TeamName(roomName, teamName).orElseGet(() -> null);
        if (findRoom == null) {
            return  null;
        }
        List<UserDto> viewers = findRoom.getViewer().stream()
                .map(user -> new UserDto(
                        user.getNickname(), user.getBirthday(), user.getGender(),
                        user.userToDto().getUserDetail()))
                        .collect(Collectors.toList()
                        );
        return RoomDto.builder()
                .viewer(viewers)
                .teamName(teamName)
                .introduce(findRoom.getIntroduce())
                .roomName(findRoom.getRoomName())
                .latIng(findRoom.getLatIng())
                .musics(findRoom.getReservationMusic())
                .teamProfileImg(findRoom.getOnAirTeam().getTeamProfileImg())
                .build();
    }

    @Transactional
    public Room reservationListRemove(String roomName, String teamName, RMusic music) {
        Room findRoom = roomRepository.findByRoomNameAndOnAirTeam_TeamName(roomName, teamName).orElseThrow(() -> {
            return new IllegalArgumentException("찾으시는 방은 존재하지 않습니다");
        });

        rMusicRepository.deleteByMusicRoomIdAndTitleAndSingerAndUserNickname(findRoom.getId(), music.getTitle(), music.getSinger(),music.getUserNickname());

        log.info("곡 삭제 team =>{}, room => {}, music=> {}", teamName, roomName, music.getId());
        return findRoom;
    }

    @Transactional
    public void deleteRoom(RoomSaveDto room) {
        Team team = teamRepository.findByTeamName(room.getTeamName()).orElseThrow(() -> {
            return new IllegalArgumentException("해당하는 Team 이 없습니다.");
        });
        team.setOnAir(false);
        team.setOnAirURL(null);
        roomRepository.deleteByRoomNameAndOnAirTeam_TeamName(room.getRoomName(), room.getTeamName());

    }
}
