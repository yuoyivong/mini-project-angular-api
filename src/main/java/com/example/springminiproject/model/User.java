package com.example.springminiproject.model;

import com.example.springminiproject.response.dto.ArticleDTO;
import com.example.springminiproject.response.dto.CommentDTO;
import com.example.springminiproject.response.dto.UserDTO;
import com.example.springminiproject.response.dto.UserEntityDTO;
import jakarta.persistence.*;
import lombok.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity(name = "user_tb")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

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

    @Enumerated(EnumType.STRING)
    private Role role;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name().toUpperCase()));
    }

    @Override
    public String getPassword() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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
