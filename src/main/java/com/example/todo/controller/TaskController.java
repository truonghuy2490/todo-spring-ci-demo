package com.example.todo.controller;

import com.example.todo.model.Task;
import com.example.todo.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TaskController {

    private final TaskService taskService;

    // Constructor-based dependency injection
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Redirect the root URL to the task list
    @GetMapping("/")
    public String home() {
        return "redirect:/todos";
    }

    // Display all tasks
    @GetMapping("/todos")
    public String listTasks(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
        return "todos";
    }

    // Display the create form
    @GetMapping("/todos/new")
    public String showCreateForm(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("pageTitle", "Add Task");
        model.addAttribute("formAction", "/todos");
        return "todo-form";
    }

    // Create a task
    @PostMapping("/todos")
    public String createTask(
            @ModelAttribute("task") Task task,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (!hasValidTitle(task)) {
            model.addAttribute("pageTitle", "Add Task");
            model.addAttribute("formAction", "/todos");
            model.addAttribute("error", "Task title must not be blank.");
            return "todo-form";
        }

        try {
            taskService.createTask(task);
            redirectAttributes.addFlashAttribute(
                    "message", "Task created successfully.");
            return "redirect:/todos";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("pageTitle", "Add Task");
            model.addAttribute("formAction", "/todos");
            model.addAttribute("error", ex.getMessage());
            return "todo-form";
        }
    }

    // Display the edit form
    @GetMapping("/todos/{id}/edit")
    public String showEditForm(
            @PathVariable Long id,
            Model model,
            RedirectAttributes redirectAttributes) {

        try {
            Task task = taskService.getTaskById(id);
            model.addAttribute("task", task);
            model.addAttribute("pageTitle", "Edit Task");
            model.addAttribute("formAction", "/todos/" + id);
            return "todo-form";
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute(
                    "error", "Task not found.");
            return "redirect:/todos";
        }
    }

    // Update an existing task's title
    @PostMapping("/todos/{id}")
    public String updateTask(
            @PathVariable Long id,
            @ModelAttribute("task") Task task,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (!hasValidTitle(task)) {
            task.setId(id);
            model.addAttribute("pageTitle", "Edit Task");
            model.addAttribute("formAction", "/todos/" + id);
            model.addAttribute("error", "Task title must not be blank.");
            return "todo-form";
        }

        try {
            taskService.updateTask(id, task);
            redirectAttributes.addFlashAttribute(
                    "message", "Task updated successfully.");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute(
                    "error", "Task not found.");
        }

        return "redirect:/todos";
    }

    // Toggle task completion
    @PostMapping("/todos/{id}/toggle")
    public String toggleTask(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        try {
            taskService.toggleTaskCompletion(id);
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute(
                    "error", "Task not found.");
        }

        return "redirect:/todos";
    }

    // Delete a task
    @PostMapping("/todos/{id}/delete")
    public String deleteTask(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        try {
            taskService.deleteTask(id);
            redirectAttributes.addFlashAttribute(
                    "message", "Task deleted successfully.");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute(
                    "error", "Task not found.");
        }

        return "redirect:/todos";
    }

    private boolean hasValidTitle(Task task) {
        return task != null
                && task.getTitle() != null
                && !task.getTitle().isBlank();
    }
}