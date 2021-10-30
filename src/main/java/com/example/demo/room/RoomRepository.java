package com.example.demo.room;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room,Long> {
    void deleteByRoomNameAndOnAirTeam_TeamName(String roomName, String teamName);

    Optional<Room> findByRoomNameAndOnAirTeam_TeamName(String roomName, String teamName);
}
