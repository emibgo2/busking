//package com.example.demo.room;
//
//import com.example.demo.user.User;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.CreationTimestamp;
//
//import javax.persistence.*;
//import java.sql.Timestamp;
//
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//@Entity
//public class Chat {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false,length = 200)
//    private String content;
//
//    @ManyToOne
//    @JoinColumn(name = "roomId")
//    private Room chatRoom;
//
//    @ManyToOne
//    @JoinColumn(name ="nickname")
//    private User user;
//
//
//
//    @CreationTimestamp
//    private Timestamp createDate;
//
//
//}
