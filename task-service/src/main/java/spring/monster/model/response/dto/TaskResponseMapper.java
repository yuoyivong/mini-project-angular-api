package spring.monster.model.response.dto;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import spring.monster.feign_client.KeycloakServiceClient;
import spring.monster.model.entity.Task;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Service
public class TaskResponseMapper {

    private final KeycloakServiceClient keycloakServiceClient;

    public TaskResponseMapper(KeycloakServiceClient keycloakServiceClient) {
        this.keycloakServiceClient = keycloakServiceClient;
    }

    @CircuitBreaker(name = "task-service-circuit-breaker", fallbackMethod = "getUserGroupFallback")
//    method reference
    public TaskResponse taskResponseDTO(Task task) {
        //        fetch created by user info by id
        UserResponse createdByUser = Objects.requireNonNull(keycloakServiceClient.getUserById(String.valueOf(task.getCreatedBy())).getBody()).getPayload();

//        fetch assigned to user info by id
        UserResponse assignedToUser = Objects.requireNonNull(keycloakServiceClient.getUserById(String.valueOf(task.getAssignedTo())).getBody()).getPayload();

//        fetch group info by group id
        GroupResponse groupResponse = Objects.requireNonNull(keycloakServiceClient.getGroupById(String.valueOf(task.getGroupId())).getBody()).getPayload();

        //        create and return task
        TaskResponse newTask = new TaskResponse();
        newTask.setTaskId(task.getId());
        newTask.setTaskName(task.getTaskName());
        newTask.setDescription(task.getDescription());
        newTask.setCreatedBy(createdByUser);
        newTask.setAssignedTo(assignedToUser);
        newTask.setGroup(groupResponse);
        newTask.setCreatedDate(task.getCreatedAt());
        newTask.setLastModified(task.getLastModified());

        return newTask;

    }

//    fallback method
    public TaskResponse getUserGroupFallback(Throwable throwable) {
        TaskResponse defaultTask = new TaskResponse();
        defaultTask.setTaskId(UUID.fromString("f31dc0c0-f231-46d1-bacc-584b5dbcd9ee"));
        defaultTask.setTaskName("Random Task");
        defaultTask.setDescription("Random Description");
        defaultTask.setCreatedBy(new UserResponse(
                "67684585-ca83-4214-a227-e77deb6005e8",
                "Lily",
                "lily@gmail.com",
                "Lily",
                "Sun"
        ));
        defaultTask.setAssignedTo(new UserResponse(
                "77978bb7-69d6-4f4b-b266-23739d0aba5a",
                "Nam",
                "nam@gmail.com",
                "Nam",
                "Tang"
        ));
        defaultTask.setGroup(new GroupResponse(
                "6b6449d4-dec0-4d0c-b5c3-da3301fd572e",
                "Random Group"
        ));
        defaultTask.setCreatedDate(new Date());
        defaultTask.setLastModified(new Date());
        return defaultTask;
    }
}
