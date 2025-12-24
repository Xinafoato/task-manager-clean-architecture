

import com.marti.application.usecases.task.CompleteTaskUseCase;
import com.marti.domain.model.Status;
import com.marti.domain.model.Task;
import com.marti.domain.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompleteTaskUseCaseTest {

    @Mock
    private TaskRepository taskRepo;

    @InjectMocks
    private CompleteTaskUseCase useCase;

    private Task task;

    @BeforeEach
    void setUp() {
        task = Task.create(
                "taskList-1",
                "Title",
                "Description",
                Status.IN_PROGRESS,
                null,
                null
        );
    }

    @Test
    void should_complete_task_when_task_exists_and_belongs_to_task_list() {
        // Arrange
        when(taskRepo.findById(task.getId())).thenReturn(Optional.of(task));

        // Act
        useCase.execute("taskList-1", task.getId());

        // Assert
        assertEquals(Status.DONE, task.getStatus());
        verify(taskRepo).save(task);
    }

    @Test
    void should_throw_exception_when_task_does_not_belong_to_task_list() {
        // Arrange
        when(taskRepo.findById(task.getId())).thenReturn(Optional.of(task));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> useCase.execute("another-list", task.getId())
        );

        assertEquals("Task does not belong to this task list", exception.getMessage());
        verify(taskRepo, never()).save(any());
    }

    @Test
    void should_throw_exception_when_task_does_not_exist() {
        // Arrange
        when(taskRepo.findById("unknown-id")).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> useCase.execute("taskList-1", "unknown-id")
        );

        assertEquals("Task not found", exception.getMessage());
        verify(taskRepo, never()).save(any());
    }
}
