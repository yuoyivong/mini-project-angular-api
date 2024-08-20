package com.example.springminiproject.model;

import com.example.springminiproject.response.dto.ArticleDTO;
import com.example.springminiproject.response.dto.CommentDTO;
import com.example.springminiproject.response.dto.UserDTO;
import com.example.springminiproject.response.dto.UserEntityDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity(name = "user_tb")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user")
    private List<Category> categoryList;

    @OneToMany(mappedBy = "user")
    private List<Bookmark> bookmarkList;

    @OneToMany(mappedBy = "user")
    private List<Comment> commentList = new ArrayList<>();

    public UserDTO userDTOResponse() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(this.userId);
        userDTO.setAddress(this.address);
        userDTO.setEmail(this.email);
        userDTO.setUsername(this.username);
        userDTO.setPhoneNumber(this.phoneNumber);
        userDTO.setCreatedAt(this.createdAt);
        userDTO.setUpdatedAt(this.updatedAt);

//        Set<CommentDTO> commentDTOList = new HashSet<>();
//
//        for(Comment c : this.commentList) {
//            commentDTOList.add(new CommentDTO(
//                    c.getCommentId(),
//                    c.getCmt(),
//                    c.getCreatedAt(),
//                    c.getUpdatedAt()
//            ));
//        }
//
//        userDTO.setCommentList(new ArrayList<>(commentDTOList));

        return userDTO;
    }

//    public UserEntityDTO userEntityDTOResponse() {
//        UserEntityDTO userEntityDTO = new UserEntityDTO();
//        userEntityDTO.setUserId(this.userId);
//        userEntityDTO.setAddress(this.address);
//        userEntityDTO.setEmail(this.email);
//        userEntityDTO.setUsername(this.username);
//        userEntityDTO.setPhoneNumber(this.phoneNumber);
//        userEntityDTO.setCreatedAt(this.createdAt);
//        userEntityDTO.setUpdatedAt(this.updatedAt);
//
//        return userEntityDTO;
//    }

}
