package com.lucas.task_manager.dto;

import java.util.List;

public record TaskPageDTO(List<TaskDTO> tasks, long totalElements, int totalPages) {}
