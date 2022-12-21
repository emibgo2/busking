package com.example.demo.room;


import com.example.demo.ResponseDto;
import com.example.demo.music.MusicRepository;
import com.example.demo.room.Rmusic.RMusic;
import com.example.demo.user.User;
import com.example.demo.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/room")
public class
RoomApiController {

    private final RoomService roomService;
    private final RoomRepository roomRepository;
    private final MusicRepository musicRepository;

    @PostMapping
    public ResponseEntity<String> save(@RequestBody RoomSaveDto room) {
        return roomService.createRoom(room);
    }

    @PostMapping("/delete")
    public void deleteRoom(@RequestBody RoomSaveDto room) {
        roomService.deleteRoom(room);
    }

    @PostMapping("/{roomName}/{teamName}/music")
    public ResponseEntity<RoomDto> musicReservation(@PathVariable String roomName, @PathVariable String teamName, @RequestBody RMusic music) {
        if ((roomName.isEmpty() || roomName =="/")|| (teamName.isEmpty() || teamName =="/")) {
            return new ResponseEntity("roomName or teamName is blank", HttpStatus.BAD_REQUEST);
        }
        return roomService.reservationMusic(roomName, teamName, music);
    }

    @PostMapping("/{roomName}/{teamName}/music/delete")
    public ResponseEntity<RoomDto> musicRemoveInReservationList(@PathVariable String roomName, @PathVariable String teamName, @RequestBody RMusic music) {
        if ((roomName.isEmpty() || roomName =="/")|| (teamName.isEmpty() || teamName =="/")) {
            return new ResponseEntity( "roomName or teamName is blank",HttpStatus.BAD_REQUEST);
        }
        Room findRoom = roomService.reservationListRemove(roomName, teamName, music);
        return new ResponseEntity(findRoom.roomToRoomDTO(),HttpStatus.OK);
    }

    @GetMapping("/{roomName}/{teamName}")
    public ResponseEntity<RoomDto> findRoom(@PathVariable String roomName, @PathVariable String teamName) {
        if ((roomName.isEmpty() || roomName =="/")|| (teamName.isEmpty() || teamName =="/")) {
            return new ResponseEntity("roomName or teamName is blank",HttpStatus.BAD_REQUEST );
        }
        RoomDto findRoom = roomService.getByRoomNameAndTeamName(roomName, teamName);
        if (findRoom == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(findRoom);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RoomDto>> roomAll() {
        return ResponseEntity.ok(roomService.getAll());
    }




}
