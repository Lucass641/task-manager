package com.lucas.task_manager.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.lucas.task_manager.enums.converters.StatusConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import com.lucas.task_manager.enums.Status;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE task SET status = 'Removida' WHERE id = ?")
@Where(clause = "status <> 'Removida'")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @NotBlank
    @NotNull
    @Length(min = 3, max = 100)
    @Column(length = 100, nullable = false, unique = true)
    private String title;

    @NotNull
    @Length(max = 300)
    @Column(length = 300, nullable = false)
    private String description;

    @Column(length = 15, nullable = false)
    @Convert(converter = StatusConverter.class)
    private Status status = Status.PENDING;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdOn;

    @FutureOrPresent
    @Column(nullable = false)
    private LocalDateTime deadline;

}
