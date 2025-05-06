package com.lucas.task_manager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import com.lucas.task_manager.enums.Status;
import com.lucas.task_manager.enums.validation.ValueOfEnum;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public record TaskDTO(
        @JsonProperty("id") Long id,
        @NotBlank @NotNull @Length(min = 3, max = 100) String title,
        @NotNull @Length(max = 300) String description,
        @ValueOfEnum(enumClass = Status.class) String status,
        @PastOrPresent LocalDateTime createdOn,
        @FutureOrPresent LocalDateTime deadline
) {
}
