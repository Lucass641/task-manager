package com.lucas.task_manager.dto.mapper;

import com.lucas.task_manager.dto.TaskDTO;
import com.lucas.task_manager.enums.Status;
import com.lucas.task_manager.model.Task;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Component
public class TaskMapper {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    public TaskDTO toDTO(Task task) {
        if (task == null) {
            return null;
        }
        String deadlineString = null;
        if (task.getDeadline() != null) {
            deadlineString = task.getDeadline().format(DATE_TIME_FORMATTER);
        }
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().getValue(),
                task.getCreatedOn(),
                deadlineString
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
        LocalDateTime createdOn = taskDTO.createdOn() != null ? taskDTO.createdOn() : LocalDateTime.now();
        task.setCreatedOn(createdOn);
        if (taskDTO.deadline() != null) {
            String deadlineStr = taskDTO.deadline().trim();
            long daysToAdd = !deadlineStr.isEmpty() ? Long.parseLong(deadlineStr) : 0;
            task.setDeadline(createdOn.plusDays(daysToAdd));
        }


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