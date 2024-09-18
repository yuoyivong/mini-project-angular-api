package spring.monster.feign_client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import spring.monster.model.response.ApiResponse;
import spring.monster.model.response.dto.GroupResponse;
import spring.monster.model.response.dto.UserResponse;

@FeignClient(name = "KEYCLOAK-ADMIN-CLIENT")
public interface KeycloakServiceClient {

    @GetMapping("/api/v1/user/{userId}")
    ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable("userId") String userId);

    @GetMapping("/api/v1/group/{groupId}")
    ResponseEntity<ApiResponse<GroupResponse>> getGroupById(@PathVariable("groupId") String groupId);

}
