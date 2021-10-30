package com.example.demo.room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomSaveDto {
    @NotNull(message = "방명은 필수!.")
    private String roomName;
    @NotNull(message = "팀명은 필수!.")
    private String teamName;
    @Lob
    private String introduce;
}
