package com.lucas.task_manager.dto.mapper;

import com.lucas.task_manager.dto.TaskDTO;
import com.lucas.task_manager.enums.Status;
import com.lucas.task_manager.model.Task;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TaskMapper {

    public TaskDTO toDTO(Task task) {
        if (task == null) {
            return null;
        }
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().getValue(),
                task.getCreatedOn(),
                task.getDeadline()
        );
    }

    public Task toEntity(TaskDTO taskDTO) {
        if (taskDTO == null) {
            return null;
        }

        Task task = new Task();
        if (taskDTO.id() != null) {
            task.setId(taskDTO.id());
        }
        task.setTitle(taskDTO.title());
        task.setDescription(taskDTO.description());
        task.setStatus(convertStatusValue(Status.PENDING.getValue()));
        task.setCreatedOn(LocalDateTime.now());
        task.setDeadline(taskDTO.deadline());

        return task;
    }


    public Status convertStatusValue(String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "Pendente" -> Status.PENDING;
            case "Em andamento" -> Status.IN_PROGRESS;
            case "Concluída" -> Status.COMPLETED;
            case "Removida" -> Status.DELETED;
            default -> throw new IllegalArgumentException("Status inválido: " + value);
        };
    }
}