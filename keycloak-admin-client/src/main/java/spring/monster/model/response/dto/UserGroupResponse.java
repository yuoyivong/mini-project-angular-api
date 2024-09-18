package spring.monster.model.response.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserGroupResponse {

    private UserResponse user;
    private GroupResponse group;

    private List<UserResponse> usersList;

}
