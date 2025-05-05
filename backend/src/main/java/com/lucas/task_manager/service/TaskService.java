package com.lucas.task_manager.service;

import com.lucas.task_manager.dto.mapper.TaskMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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

import java.time.LocalDateTime;
import java.util.List;

@Validated
@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public TaskPageDTO list(@PositiveOrZero int page, @Positive @Max(100) int pageSize) {
        Page<Task> taskPage = taskRepository.findAll(PageRequest.of(page, pageSize));
        List<TaskDTO> tasks = taskPage.get().map(taskMapper::toDTO).toList();
        return new TaskPageDTO(tasks, taskPage.getTotalElements(), taskPage.getTotalPages());
    }

    public TaskDTO findById(@NotNull @Positive Long id) {
        return taskRepository.findById(id)
                .map(taskMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada com ID " + id));
    }

    public TaskDTO create(@Valid @NotNull TaskDTO dto) {
        Task entity = taskMapper.toEntity(dto);
        entity.setCreatedOn(LocalDateTime.now());
        return taskMapper.toDTO(taskRepository.save(entity));
    }

    public TaskDTO update(@NotNull @Positive Long id, @Valid @NotNull TaskDTO dto) {
        return taskRepository.findById(id)
                .map(existing -> {
                    Task updated = taskMapper.toEntity(dto);
                    existing.setTitle(updated.getTitle());
                    existing.setDescription(updated.getDescription());
                    existing.setStatus(updated.getStatus());
                    existing.setDeadline(updated.getDeadline());
                    return taskMapper.toDTO(taskRepository.save(existing));
                }).orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada com ID " + id));
    }

    public void delete(@NotNull @Positive Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada com ID " + id));
        taskRepository.delete(task);
    }
}
