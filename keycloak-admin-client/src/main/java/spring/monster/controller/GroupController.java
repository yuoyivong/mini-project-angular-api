package spring.monster.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.monster.model.request.GroupRequest;
import spring.monster.model.response.ApiResponse;
import spring.monster.model.response.dto.GroupResponse;
import spring.monster.model.response.dto.UserGroupResponse;
import spring.monster.service.GroupService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@SecurityRequirement(name = "Oauth2KeycloakAuth")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    //    create new group
    @PostMapping("/group")
    public ResponseEntity<ApiResponse<GroupResponse>> insertNewGroup(@RequestBody GroupRequest groupRequest) {
        ApiResponse<GroupResponse> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.CREATED);
        apiResponse.setMessage("Group inserted successfully");
        apiResponse.setPayload(groupService.createNewGroup(groupRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

//    get all groups
    @GetMapping("/groups")
    public ResponseEntity<ApiResponse<List<GroupResponse>>> getAllGroups() {
        ApiResponse<List<GroupResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Get all groups successfully");
        apiResponse.setPayload(groupService.getAllGroups());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

//    get group by id
    @GetMapping("/group/{groupId}")
    public ResponseEntity<ApiResponse<GroupResponse>> getGroupById(@PathVariable("groupId") String groupId) {
        ApiResponse<GroupResponse> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Get group with id " + groupId + " successfully");
        apiResponse.setPayload(groupService.getGroupByGroupId(groupId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

//    delete group by id
    @DeleteMapping("/group/{groupId}")
    public ResponseEntity<ApiResponse<Void>> deleteGroupById(@PathVariable("groupId") String groupId) {
        groupService.deleteGroupById(groupId);
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Delete group with id " + groupId + " successfully");

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


//    assign user to group
    @PostMapping("/group/{groupId}/user/{userId}")
    public ResponseEntity<ApiResponse<UserGroupResponse>> addUserToGroup(@PathVariable("userId") String userId, @PathVariable("groupId") String groupId) {
        ApiResponse<UserGroupResponse> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Add user to group with id " + groupId + " successfully");
        apiResponse.setPayload(groupService.assignUserToGroup(userId, groupId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

//    get all users by group id
    @GetMapping("/group/{groupId}/users")
    public ResponseEntity<ApiResponse<UserGroupResponse>> getUsersInGroup(@PathVariable("groupId") String groupId) {
        ApiResponse<UserGroupResponse> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Get users in group with id " + groupId + " successfully");
        apiResponse.setPayload(groupService.getUsersListByGroupId(groupId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

//    update group's name by group id
    @PutMapping("/group/{groupId}")
    public ResponseEntity<ApiResponse<GroupResponse>> updateGroupById(@PathVariable("groupId") String groupId, @RequestBody GroupRequest groupRequest) {
        ApiResponse<GroupResponse> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Update group with id " + groupId + " successfully");
        apiResponse.setPayload(groupService.updateGroupNameByGroupId(groupId, groupRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
