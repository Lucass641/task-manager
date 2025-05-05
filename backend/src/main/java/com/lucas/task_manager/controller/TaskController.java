package com.lucas.task_manager.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import com.lucas.task_manager.dto.TaskDTO;
import com.lucas.task_manager.dto.TaskPageDTO;
import com.lucas.task_manager.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Validated
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public TaskPageDTO list(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive @Max(100) int pageSize) {
        return taskService.list(page, pageSize);
    }

    @GetMapping("/{id}")
    public TaskDTO findById(@PathVariable @NotNull @Positive Long id) {
        return taskService.findById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public TaskDTO create(@RequestBody @Valid @NotNull TaskDTO task) {
        return taskService.create(task);
    }

    @PutMapping("/{id}")
    public TaskDTO update(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid TaskDTO task) {
        return taskService.update(id, task);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NotNull @Positive Long id) {
        taskService.delete(id);
    }
}
