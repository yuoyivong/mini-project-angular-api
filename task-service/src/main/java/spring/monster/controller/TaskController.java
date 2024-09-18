package spring.monster.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.monster.model.request.TaskRequest;
import spring.monster.model.response.ApiResponse;
import spring.monster.model.response.dto.TaskResponse;
import spring.monster.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@SecurityRequirement(name = "Oauth2KeycloakAuth")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

//    create new task
    @PostMapping("/task")
    public ResponseEntity<ApiResponse<TaskResponse>> createNewTask(@RequestBody TaskRequest taskRequest) {
        ApiResponse<TaskResponse> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.CREATED);
        apiResponse.setMessage("A new task has been created successfully.");
        apiResponse.setPayload(taskService.createTask(taskRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

//    get all tasks
    @GetMapping("/tasks")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getAllTasks(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "taskName") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {
        ApiResponse<List<TaskResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Get all tasks successfully.");
        apiResponse.setPayload(taskService.getAllTasks(pageNo, pageSize, sortBy, sortDirection));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

//    get task by id
    @GetMapping("/task/{taskId}")
    public ResponseEntity<ApiResponse<TaskResponse>> getTaskById(@PathVariable("taskId") String taskId) {
        ApiResponse<TaskResponse> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Get task with id " + taskId + " successfully.");
        apiResponse.setPayload(taskService.getTaskById(taskId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

//    update task by id
    @PutMapping("/task/{taskId}")
    public ResponseEntity<ApiResponse<TaskResponse>> updateTaskById(@PathVariable("taskId") String taskId, @RequestBody TaskRequest taskRequest) {
        taskService.updateTaskById(taskId, taskRequest);
        ApiResponse<TaskResponse> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Update task with id " + taskId + " successfully.");
        apiResponse.setPayload(taskService.getTaskById(taskId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

//    delete task by id
    @DeleteMapping("/task/{taskId}")
    public ResponseEntity<ApiResponse<TaskResponse>> deleteTaskById(@PathVariable("taskId") String taskId) {
        taskService.deleteTaskById(taskId);
        ApiResponse<TaskResponse> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("Delete task with id " + taskId + " successfully.");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
