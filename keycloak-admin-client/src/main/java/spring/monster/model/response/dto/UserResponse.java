package spring.monster.model.response.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    private UUID userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Date createdAt;
    private Date lastModifiedAt;

    public static UserResponse userResponseDTO(UserRepresentation userRepresentation) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(UUID.fromString(userRepresentation.getId()));
        userResponse.setUsername(userRepresentation.getUsername());
        userResponse.setEmail(userRepresentation.getEmail());
        userResponse.setFirstName(userRepresentation.getFirstName());
        userResponse.setLastName(userRepresentation.getLastName());
        userResponse.setCreatedAt(new Date());
        userResponse.setLastModifiedAt(new Date());
        return userResponse;
    }

}
