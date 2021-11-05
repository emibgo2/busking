package com.example.demo.room;

import com.example.demo.ResponseDto;
import com.example.demo.music.Music;
import com.example.demo.room.Rmusic.RMusic;
import com.example.demo.room.Rmusic.RMusicRepository;
import com.example.demo.room.chat.ChatRepository;
import com.example.demo.room.chat.ChatSaveRequestDto;
import com.example.demo.team.Team;
import com.example.demo.team.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private final ChatRepository chatRepository;
    private final RoomRepository roomRepository;
    private final TeamRepository teamRepository;
    private final RMusicRepository rMusicRepository;

    @Transactional
    public void writeChat(ChatSaveRequestDto chatSaveRequestDto
    ) {
        chatRepository.mSave(chatSaveRequestDto.getNickname(), chatSaveRequestDto.getRoomId(), chatSaveRequestDto.getContent());
    }

    @Transactional
    public ResponseDto<String> createRoom(RoomSaveDto room) {

        Team team = teamRepository.findByTeamName(room.getTeamName()).orElseThrow(() -> {
            return new IllegalArgumentException("해당하는 Team 이 없습니다.");
        });
        if ( team.getOnAirURL() != null) {
            return new ResponseDto<>(HttpStatus.NOT_ACCEPTABLE.value(),"이미 방송중인 팀입니다.") ;
        }
        team.setOnAirURL("https://busking-back.herokuapp.com/room/" + room.getRoomName() + "/" + room.getTeamName());
        team.setOnAir(true);

        roomRepository.save(new Room(room.getRoomName(), team, room.getIntroduce(),team.getTeamProfileImg()));

        log.info("Create Room! Room Information: {}",room);
        return new ResponseDto<>(HttpStatus.OK.value(), "https://busking-back.herokuapp.com/room/" + room.getRoomName() + "/" + room.getTeamName());
    }


    @Transactional
    public ResponseDto<RoomDto> reservationMusic(String roomName, String teamName, Music music) {
        Room findRoom = roomRepository.findByRoomNameAndOnAirTeam_TeamName(roomName, teamName).orElseThrow(() -> {
            return new IllegalArgumentException("찾으시는 방은 존재하지 않습니다");
        });
        System.out.println("music = " + music);

        RMusic reserMusic = rMusicRepository.findByMusicRoomIdAndTitleAndSinger(findRoom.getId(), music.getTitle(), music.getSinger()).orElseGet(() -> {
            return new RMusic();
        });
        if (reserMusic.getSinger().isEmpty()) {
            RMusic rMusic = music.musicToRMusic(music);
            rMusic.setMusicRoom(findRoom);
            rMusicRepository.save(rMusic);
            findRoom.getReservationMusic().add(rMusic);
            return new ResponseDto<>(HttpStatus.OK.value(), findRoom.roomToRoomDTO());
        } else return new ResponseDto<>(HttpStatus.NOT_ACCEPTABLE.value(), findRoom.roomToRoomDTO());



    }

    @Transactional
    public Room reservationListRemove(String roomName, String teamName, Music music) {
        Room findRoom = roomRepository.findByRoomNameAndOnAirTeam_TeamName(roomName, teamName).orElseThrow(() -> {
            return new IllegalArgumentException("찾으시는 방은 존재하지 않습니다");
        });

        rMusicRepository.deleteByMusicRoomIdAndTitleAndSinger(findRoom.getId(), music.getTitle(), music.getSinger());

        log.info("삭제");
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
