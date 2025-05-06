package com.lucas.task_manager.service;

import com.lucas.task_manager.dto.mapper.TaskMapper;
import com.lucas.task_manager.exception.RecordNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import com.lucas.task_manager.dto.TaskDTO;
import com.lucas.task_manager.dto.TaskPageDTO;
import com.lucas.task_manager.model.Task;
import com.lucas.task_manager.repository.TaskRepository;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;


@Validated
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public TaskPageDTO list(@PositiveOrZero int page, @Positive @Max(100) int pageSize) {
        Page<Task> pageTask = taskRepository.findAll(PageRequest.of(page, pageSize));
        List<TaskDTO> tasks = pageTask.get().map(taskMapper::toDTO).toList();
        return new TaskPageDTO(tasks, pageTask.getTotalElements(), pageTask.getTotalPages());
    }

    public TaskDTO findById(@NotNull @Positive Long id) {
        return taskRepository.findById(id)
                .map(taskMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public TaskDTO create(@Valid @NotNull TaskDTO task) {
        return taskMapper.toDTO(taskRepository.save(taskMapper.toEntity(task)));
    }

    public TaskDTO update(@NotNull @Positive Long id, @Valid @NotNull TaskDTO taskDTO) {
        return taskRepository.findById(id)
                .map(recordFound -> {
                    recordFound.setTitle(taskDTO.title());
                    recordFound.setDescription(taskDTO.description());
                    recordFound.setStatus(this.taskMapper.convertStatusValue(taskDTO.status()));
                    recordFound.setDeadline(taskDTO.deadline());
                    return taskMapper.toDTO(taskRepository.save(recordFound));
                }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void delete(@NotNull @Positive Long id) {
        taskRepository.delete(taskRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id)));
    }
}
