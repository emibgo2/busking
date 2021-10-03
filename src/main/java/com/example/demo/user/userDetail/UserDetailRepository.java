package com.example.demo.user.userDetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
    @Modifying
    @Query(value = "INSERT INTO userdetail(userId,profileImgURL,introduce) VALUES(?1,?2,?3)",nativeQuery = true)
    void mSave(Long userId, String profileImgURL, String introduce);
}
