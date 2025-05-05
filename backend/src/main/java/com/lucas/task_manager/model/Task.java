package com.lucas.task_manager.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import com.lucas.task_manager.enums.Status;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE task SET status = 'DELETED' WHERE id = ?")
@Where(clause = "status NOT IN ('PENDING', 'IN_PROGRESS', 'COMPLETED')")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @NotBlank
    @Size(min = 3, max = 100)
    @Column(length = 100, nullable = false, unique = true)
    private String title;

    @Size(max = 500)
    @Column(length = 500)
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Status status = Status.PENDING;

    @NotNull
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdOn;

    @NotNull
    @FutureOrPresent
    @Column(nullable = false)
    private LocalDateTime deadline;

}
