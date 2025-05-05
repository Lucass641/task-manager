package com.lucas.task_manager;

import com.lucas.task_manager.enums.Status;
import com.lucas.task_manager.model.Task;
import com.lucas.task_manager.repository.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class TaskManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskManagerApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(TaskRepository taskRepository) {
        return args -> {
            taskRepository.deleteAll();

            for (int i = 1; i <= 20; i++) {
                Task task = new Task();
                task.setTitle("Tarefa " + i);
                task.setDescription("Descrição da Tarefa " + i);
                task.setStatus(Status.PENDING);
                task.setCreatedOn(LocalDateTime.now());
                task.setDeadline(LocalDateTime.now().plusDays(7));

                taskRepository.save(task);
            }
        };
    }
    }