package com.example.springminiproject.response.dto;

import com.example.springminiproject.model.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserDTO {

    private Long userId;
    private String username;
    private String email;
    private String address;
    private String phoneNumber;
    private LocalDateTime createdAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime updatedAt;

    private Role role;

//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    private List<ArticleDTO> articleList;
//
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    private List<CommentDTO> commentList;

}
