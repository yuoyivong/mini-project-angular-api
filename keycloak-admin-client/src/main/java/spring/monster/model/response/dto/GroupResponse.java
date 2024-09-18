package spring.monster.model.response.dto;

import lombok.*;
import org.keycloak.representations.idm.GroupRepresentation;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GroupResponse {

    private UUID groupId;
    private String groupName;

    public static GroupResponse responseGroupDTO(GroupRepresentation groupRepresentation) {
        GroupResponse response = new GroupResponse();
        response.setGroupId(UUID.fromString(groupRepresentation.getId()));
        response.setGroupName(groupRepresentation.getName());

        return response;
    }

}
