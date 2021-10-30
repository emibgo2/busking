package com.example.demo.room.chat;


import com.example.demo.room.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ChatRepository extends JpaRepository<Chat,Long> {

    @Modifying
    @Query(value = "INSERT INTO reply(nickname,roomId,content,createDate) VALUES(?1,?2,?3,now())",nativeQuery = true)
    int mSave(String nickname, Long roomId,String cotent) ;

}
