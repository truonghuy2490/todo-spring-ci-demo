package com.example.todo.service;

import com.example.todo.model.Task;
import com.example.todo.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    // Constructor-based dependency injection
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // 1. Retrieve all tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // 2. Retrieve a task by ID
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Task not found with ID: " + id));
    }

    // 3. Create a task
    public Task createTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task must not be null");
        }

        task.setTitle(validateTitle(task.getTitle()));
        task.setId(null);
        task.setCompleted(false);

        return taskRepository.save(task);
    }

    // 4. Update an existing task's title
    public Task updateTask(Long id, Task updatedTask) {
        if (updatedTask == null) {
            throw new IllegalArgumentException("Updated task must not be null");
        }

        Task existingTask = getTaskById(id);
        existingTask.setTitle(validateTitle(updatedTask.getTitle()));

        return taskRepository.save(existingTask);
    }

    // 5. Delete a task
    public void deleteTask(Long id) {
        Task existingTask = getTaskById(id);
        taskRepository.delete(existingTask);
    }

    // 6. Toggle completion status
    public Task toggleTaskCompletion(Long id) {
        Task task = getTaskById(id);
        task.setCompleted(!task.isCompleted());

        return taskRepository.save(task);
    }

    // Validate and normalize the title
    private String validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException(
                    "Task title must not be null or blank");
        }

        return title.trim();
    }
}