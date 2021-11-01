package com.example.demo.room;


import com.example.demo.ResponseDto;
import com.example.demo.music.Music;
import com.example.demo.music.MusicRepository;
import com.example.demo.room.chat.ChatSaveRequestDto;
import com.example.demo.user.User;
import com.example.demo.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/room")
public class RoomApiController {

    private final RoomService roomService;
    private final RoomRepository roomRepository;
    private final MusicRepository musicRepository;

    @PostMapping
    public ResponseDto<String> save(@RequestBody RoomSaveDto room) {
        // 공지사항을 저장하는 메소드

        // Room의 내용과 작성 유저의 정보를 DB에 저장
        return roomService.createRoom(room);
    }

    @DeleteMapping
    public ResponseDto<Integer> deleteRoom(@RequestBody RoomSaveDto room) {
        // 공지사항을 저장하는 메소드
        roomService.deleteRoom(room);
        // Room의 내용과 작성 유저의 정보를 DB에 저장
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PostMapping("/{roomName}/{teamName}/music")
    public ResponseDto<RoomDto> musicReservation(@PathVariable String roomName, @PathVariable String teamName, @RequestBody Music music) {
        return roomService.reservationMusic(roomName, teamName, music);
    }

    @DeleteMapping("/{roomName}/{teamName}/music")
    public ResponseDto<RoomDto> musicRemoveInReservationList(@PathVariable String roomName, @PathVariable String teamName, @RequestBody Music music) {
        Room findRoom = roomService.reservationListRemove(roomName, teamName, music);
        return new ResponseDto<>(HttpStatus.OK.value(), findRoom.roomToRoomDTO());
    }

    @GetMapping("/{roomName}/{teamName}")
    public ResponseDto<RoomDto> findRoom(@PathVariable String roomName, @PathVariable String teamName) {
        Room findRoom = roomRepository.findByRoomNameAndOnAirTeam_TeamName(roomName, teamName).orElseThrow(()->{
            return new IllegalArgumentException("찾으시는 방은 존재하지 않습니다");
        });

        List<UserDto> viewerDto = new User().userListToDtoList(findRoom.getViewer());
        return new ResponseDto<>(HttpStatus.OK.value(), findRoom.roomToRoomDTO());
    }

    @GetMapping("/all")
    public ResponseDto<List<RoomSaveDto>> roomAll() {
        List<Room> all = roomRepository.findAll();
        List<RoomSaveDto> roomall = new ArrayList<>();

        for (Room room : all) {
            roomall.add(new RoomSaveDto(room.getRoomName(), room.getOnAirTeam().getTeamName(), room.getIntroduce()));
        }
        return new ResponseDto<>(HttpStatus.OK.value(), roomall);
    }


    @PostMapping("/{roomId}/reply")

    public ResponseDto<Integer> replySave(@RequestBody ChatSaveRequestDto chatSaveRequestDto) {
        roomService.writeChat(chatSaveRequestDto);

        // DTO(User ID, Board ID, Content)를 받아 Reply 테이블에 저장
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

}
