package spring.monster.model.response.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserResponse {

    private String userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;

}
