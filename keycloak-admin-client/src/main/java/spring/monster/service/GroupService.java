package spring.monster.service;

import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.GroupsResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import spring.monster.model.request.GroupRequest;
import spring.monster.model.response.dto.GroupResponse;
import spring.monster.model.response.dto.UserGroupResponse;
import spring.monster.model.response.dto.UserResponse;

import java.net.URI;
import java.util.List;

@Service
public class GroupService {

    private final Keycloak keycloak;
    private final UserService userService;

    @Value("${keycloak.realm}")
    private String realm;

    public GroupService(Keycloak keycloak, UserService userService) {
        this.keycloak = keycloak;
        this.userService = userService;
    }

//    create new group in keycloak
    public GroupResponse createNewGroup(GroupRequest groupRequest) {
        GroupRepresentation group = new GroupRepresentation();
        group.setName(groupRequest.getGroupName());

        RealmResource realmResource = keycloak.realm(realm);
        GroupsResource groupResource = realmResource.groups();

        Response response = groupResource.add(group);
        if (response.getStatus() != 201) {
            throw new RuntimeException("Failed to create user");
        }

        URI location = response.getLocation();
        String groupId = location.getPath().replaceAll(".*/([^/]+)$", "$1");

        GroupRepresentation createdGroup = groupResource.group(groupId).toRepresentation();

        return GroupResponse.responseGroupDTO(createdGroup);
    }

//    get all groups
    public List<GroupResponse> getAllGroups() {
        List<GroupRepresentation> groupRepresentationList = keycloak.realm(realm).groups().groups();
        return groupRepresentationList.stream().map(GroupResponse::responseGroupDTO).toList();
    }

//    get group by id
    public GroupResponse getGroupByGroupId(String groupId) {
        GroupRepresentation groupRepresentation = keycloak.realm(realm).groups().group(groupId).toRepresentation();
        return GroupResponse.responseGroupDTO(groupRepresentation);
    }

//    delete group by id
    public void deleteGroupById(String groupId) {
        keycloak.realm(realm).groups().group(groupId).remove();
    }

//    assign user to group
    public UserGroupResponse assignUserToGroup(String userId, String groupId) {
        UserGroupResponse userGroupResponse = new UserGroupResponse();

        UserResponse userInfo = userService.getUserById(userId);
        GroupResponse groupInfo = getGroupByGroupId(groupId);

        userGroupResponse.setUser(userInfo);
        userGroupResponse.setGroup(groupInfo);

        keycloak.realm(realm).users().get(userId).joinGroup(groupId);
        return userGroupResponse;
    }

//    get all users from group via groupId
    public UserGroupResponse getUsersListByGroupId(String groupId) {
        UserGroupResponse userGroupResponse = new UserGroupResponse();

        GroupResponse groupInfo = getGroupByGroupId(groupId);

//        get users by group id
        List<UserRepresentation> userRepresentationList = keycloak.realm(realm)
                .groups().group(groupId).members();

        List<UserResponse> userResponses = userRepresentationList.stream().map(UserResponse::userResponseDTO).toList();

        userGroupResponse.setGroup(groupInfo);
        userGroupResponse.setUsersList(userResponses);

        return userGroupResponse;

    }

//    update group name by group id
    public GroupResponse updateGroupNameByGroupId(String groupId, GroupRequest groupRequest) {
        RealmResource realmResource = keycloak.realm(realm);
        GroupsResource groupResource = realmResource.groups();

        GroupRepresentation groupRepresentation = groupResource.group(groupId).toRepresentation();
        groupRepresentation.setName(groupRequest.getGroupName());

       groupResource.group(groupId).update(groupRepresentation);

       return GroupResponse.responseGroupDTO(groupRepresentation);

    }
}
