package com.example.todo.service;

import com.example.todo.model.Task;
import com.example.todo.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task(1L, "Buy milk", false);
    }

    @Test
    void getAllTasks_returnsAllTasks() {
        // Arrange
        when(taskRepository.findAll()).thenReturn(List.of(task));

        // Act
        List<Task> result = taskService.getAllTasks();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Buy milk", result.get(0).getTitle());
        verify(taskRepository).findAll();
    }

    @Test
    void getTaskById_existingId_returnsTask() {
        // Arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        // Act
        Task result = taskService.getTaskById(1L);

        // Assert
        assertSame(task, result);
        verify(taskRepository).findById(1L);
    }

    @Test
    void getTaskById_missingId_throwsException() {
        // Arrange
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> taskService.getTaskById(99L)
        );
    }

    @Test
    void createTask_validTask_savesIncompleteTaskWithTrimmedTitle() {
        // Arrange
        Task newTask = new Task(50L, "  Buy milk  ", true);
        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);

        when(taskRepository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Task result = taskService.createTask(newTask);

        verify(taskRepository).save(captor.capture());

        Task savedTask = captor.getValue();
        
        // Assert
        assertNull(savedTask.getId());
        assertEquals("Buy milk", savedTask.getTitle());
        assertFalse(savedTask.isCompleted());
        assertSame(newTask, result);
    }

    @Test
    void createTask_nullTask_throwsException() {
        // Arrange
        // taskRepository was Mock by @Mock.

        // Act + Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> taskService.createTask(null)
        );

        // Assert
        verifyNoInteractions(taskRepository);
    }

    @Test
    void createTask_blankTitle_throwsException() {
        // Arrange
        Task invalidTask = new Task(null, "   ", false);

        // Act + Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> taskService.createTask(invalidTask)
        );

        verifyNoInteractions(taskRepository);
    }

    @Test
    void updateTask_validTask_updatesTitleAndPreservesCompletion() {
        // Arrange
        task.setCompleted(true);
        Task updatedTask = new Task(null, "  Buy bread  ", false);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Task result = taskService.updateTask(1L, updatedTask);

        // Assert
        assertEquals(1L, result.getId());
        assertEquals("Buy bread", result.getTitle());
        assertTrue(result.isCompleted());
        verify(taskRepository).save(task);
    }

    @Test
    void updateTask_nullTask_throwsException() {
        // Arrange
        // Act + Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> taskService.updateTask(1L, null)
        );
        verifyNoInteractions(taskRepository);
    }

    @Test
    void updateTask_blankTitle_throwsException() {
        // Arrange
        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));

        Task invalidTask = new Task(null, "   ", false);

        // Act + Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> taskService.updateTask(1L, invalidTask)
        );
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void updateTask_missingId_throwsException() {
        // Arrange
        when(taskRepository.findById(99L))
                .thenReturn(Optional.empty());

        Task updatedTask = new Task(null, "Buy bread", false);

        // Act + Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> taskService.updateTask(99L, updatedTask)
        );

        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void deleteTask_existingId_deletesTask() {
        // Arrange
        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));

        // Act
        taskService.deleteTask(1L);

        // Assert
        verify(taskRepository).delete(task);
    }

    @Test
    void deleteTask_missingId_throwsException() {
        // Arrange
        when(taskRepository.findById(99L))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> taskService.deleteTask(99L)
        );
        verify(taskRepository, never()).delete(any(Task.class));
    }

    @Test
    void toggleTaskCompletion_twice_restoresOriginalStatus() {
        // Arrange
        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));

        when(taskRepository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Assert: Initial status is incomplete.
        assertFalse(task.isCompleted());

        // Act: First toggle
        Task firstResult = taskService.toggleTaskCompletion(1L);

        // Assert: Status becomes complete.
        assertTrue(firstResult.isCompleted());

        // Act: Second toggle
        Task secondResult = taskService.toggleTaskCompletion(1L);

        // Assert: Status returns to incomplete.
        assertFalse(secondResult.isCompleted());

        // Assert: Verify repository interactions.
        verify(taskRepository, times(2)).findById(1L);
        verify(taskRepository, times(2)).save(task);
    }
}