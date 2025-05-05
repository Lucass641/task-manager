package com.lucas.task_manager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import com.lucas.task_manager.enums.Status;
import com.lucas.task_manager.enums.validation.ValueOfEnum;

import java.time.LocalDateTime;

public record TaskDTO(
        @JsonProperty("id") Long id,

        @NotBlank @Size(min = 3, max = 100)
        String title,

        @Size(max = 500)
        String description,

        @NotBlank
        @ValueOfEnum(enumClass = Status.class)
        String status,

        @PastOrPresent
        LocalDateTime createdOn,

        @FutureOrPresent
        LocalDateTime deadline
) {
}
