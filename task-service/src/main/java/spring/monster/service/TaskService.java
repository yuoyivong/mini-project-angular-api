package spring.monster.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import spring.monster.model.entity.Task;
import spring.monster.model.request.TaskRequest;
import spring.monster.model.response.dto.TaskResponse;
import spring.monster.model.response.dto.TaskResponseMapper;
import spring.monster.repository.TaskRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskResponseMapper taskResponseMapper;

    public TaskService(TaskRepository taskRepository, TaskResponseMapper taskResponseMapper) {
        this.taskRepository = taskRepository;
        this.taskResponseMapper = taskResponseMapper;
    }

//    create task
    public TaskResponse createTask(TaskRequest taskRequest) {

        Task savedTask = new Task();
        savedTask.setId(UUID.randomUUID());
        savedTask.setTaskName(taskRequest.getTaskName());
        savedTask.setDescription(taskRequest.getDescription());
        savedTask.setCreatedBy(UUID.fromString(taskRequest.getCreatedBy()));
        savedTask.setAssignedTo(UUID.fromString(taskRequest.getAssignedTo()));
        savedTask.setGroupId(UUID.fromString(taskRequest.getGroupId()));
        savedTask.setCreatedAt(new Date());
        savedTask.setLastModified(new Date());
        Task task = taskRepository.save(savedTask);

        return taskResponseMapper.taskResponseDTO(task);
    }

//    get all tasks with pagination
    public List<TaskResponse> getAllTasks(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Task> taskPage = taskRepository.findAll(pageable);
        List<Task> tasksList = taskPage.getContent();

        return tasksList.stream().map(taskResponseMapper::taskResponseDTO).collect(Collectors.toList());

    }

//    get task by task id
    public TaskResponse getTaskById(String taskId) {
        Task task = taskRepository.findById(UUID.fromString(taskId)).orElseThrow();
        return taskResponseMapper.taskResponseDTO(task);
    }

//    delete task by id
    public void deleteTaskById(String taskId) {
        taskRepository.deleteById(UUID.fromString(taskId));
    }

//    update task by id
    public void updateTaskById(String taskId, TaskRequest taskRequest) {

        taskRepository.updateTaskByTaskId(UUID.fromString(taskId),
                taskRequest.getTaskName(),
                taskRequest.getDescription(),
                UUID.fromString(taskRequest.getCreatedBy()),
                UUID.fromString(taskRequest.getAssignedTo()),
                UUID.fromString(taskRequest.getGroupId()),
                new Date());

    }

}
