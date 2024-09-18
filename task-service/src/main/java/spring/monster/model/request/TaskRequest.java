package spring.monster.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskRequest {

    private String taskName;
    private String description;
    private String createdBy;
    private String assignedTo;
    private String groupId;

}
