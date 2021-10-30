package com.example.demo.room.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatSaveRequestDto {
    private String nickname;
    private Long roomId;
    private String content;
}

