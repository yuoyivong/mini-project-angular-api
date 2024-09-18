package spring.monster.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, name = "task_name")
    private String taskName;

    private String description;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "assigned_to")
    private UUID assignedTo;

    @Column(name = "group_id")
    private UUID groupId;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "last_modified")
    private Date lastModified;



}
