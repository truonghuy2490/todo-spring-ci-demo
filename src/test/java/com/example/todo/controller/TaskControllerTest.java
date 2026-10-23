
package com.example.todo.controller;

import com.example.todo.model.Task;
import com.example.todo.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import org.springframework.ui.ConcurrentModel;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private Model model;
    private RedirectAttributesModelMap redirectAttributes;

    @BeforeEach
    void setUp() {
        model = new ConcurrentModel();
        redirectAttributes = new RedirectAttributesModelMap();
    }

    @Test
    void home_shouldRedirectToTodos() {
        String view = taskController.home();

        assertEquals("redirect:/todos", view);
    }

    @Test
    void listTasks_shouldReturnTodosView() {
        String view = taskController.listTasks(model);

        assertEquals("todos", view);
        assertNotNull(model.getAttribute("tasks"));
        verify(taskService).getAllTasks();
    }

    @Test
    void showCreateForm_shouldPrepareForm() {
        String view = taskController.showCreateForm(model);

        assertEquals("todo-form", view);
        assertNotNull(model.getAttribute("task"));
        assertEquals("Add Task", model.getAttribute("pageTitle"));
        assertEquals("/todos", model.getAttribute("formAction"));
    }

    @Test
    void createTask_withBlankTitle_shouldReturnFormWithError() {
        Task task = new Task();
        task.setTitle("   ");

        String view = taskController.createTask(
                task, model, redirectAttributes);

        assertEquals("todo-form", view);
        assertEquals(
                "Task title must not be blank.",
                model.getAttribute("error"));
        verifyNoInteractions(taskService);
    }

    @Test
    void createTask_withValidTitle_shouldRedirectToTodos() {
        Task task = new Task();
        task.setTitle("Read a book");

        String view = taskController.createTask(
                task, model, redirectAttributes);

        assertEquals("redirect:/todos", view);
        assertEquals(
                "Task created successfully.",
                redirectAttributes.getFlashAttributes().get("message"));

        verify(taskService).createTask(task);
    }

    @Test
    void createTask_whenServiceThrows_shouldReturnFormWithError() {
        Task task = new Task();
        task.setTitle("Read a book");

        when(taskService.createTask(task))
                .thenThrow(new IllegalArgumentException(
                        "Task title already exists."));

        String view = taskController.createTask(
                task, model, redirectAttributes);

        assertEquals("todo-form", view);
        assertEquals(
                "Task title already exists.",
                model.getAttribute("error"));
    }
}
