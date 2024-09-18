package spring.monster.model.response.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.keycloak.representations.idm.RoleRepresentation;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RealmRolesResponse {

    private UUID roleId;
    private String roleName;
    private String description;

    public static RealmRolesResponse rolesResponseDTO(RoleRepresentation roleRepresentation) {
        RealmRolesResponse realmRolesResponse = new RealmRolesResponse();
        realmRolesResponse.setRoleId(UUID.fromString(roleRepresentation.getId()));
        realmRolesResponse.setRoleName(roleRepresentation.getName());
        realmRolesResponse.setDescription(roleRepresentation.getDescription());
        return realmRolesResponse;
    }

}
