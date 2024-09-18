package spring.monster.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.monster.model.entity.Task;

import java.util.Date;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = """
        UPDATE task
        SET task_name = :taskName,
            description = :description,
            created_by = :createdBy,
            assigned_to = :assignedTo,
            group_id = :groupId,
            last_modified = :lastModified
        WHERE id = :taskId
    """, nativeQuery = true)
    void updateTaskByTaskId(UUID taskId, String taskName, String description, UUID createdBy, UUID assignedTo, UUID groupId, Date lastModified);

}
