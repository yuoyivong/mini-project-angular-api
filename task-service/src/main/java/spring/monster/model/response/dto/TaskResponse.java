package spring.monster.model.response.dto;

import lombok.*;
import spring.monster.feign_client.KeycloakServiceClient;
import spring.monster.model.request.TaskRequest;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TaskResponse {

    private UUID taskId;
    private String taskName;
    private String description;
    private UserResponse createdBy;
    private UserResponse assignedTo;
    private GroupResponse group;
    private Date createdDate;
    private Date lastModified;

}
