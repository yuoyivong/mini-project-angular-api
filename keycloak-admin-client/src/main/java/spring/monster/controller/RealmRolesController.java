//package spring.monster.controller;
//
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import spring.monster.model.request.RealmRolesRequest;
//import spring.monster.model.response.ApiResponse;
//import spring.monster.model.response.dto.RealmRolesResponse;
//import spring.monster.service.RealmRolesService;
//
//@RestController
//@RequestMapping("/api/v1")
//@SecurityRequirement(name = "Oauth2KeycloakAuth")
//public class RealmRolesController {
//
//    private final RealmRolesService realmRolesService;
//
//    public RealmRolesController(RealmRolesService realmRolesService) {
//        this.realmRolesService = realmRolesService;
//    }
//
////    create new role
//    @PostMapping("/realm-role")
//    public ResponseEntity<ApiResponse<RealmRolesResponse>> createNewRole(@RequestBody RealmRolesRequest rolesRequest) {
//        ApiResponse<RealmRolesResponse> apiResponse = new ApiResponse<>();
//        apiResponse.setStatus(HttpStatus.CREATED);
//        apiResponse.setMessage("A new role is created successfully");
//        apiResponse.setPayload(realmRolesService.createNewRole(rolesRequest));
//        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
//    }
//}
