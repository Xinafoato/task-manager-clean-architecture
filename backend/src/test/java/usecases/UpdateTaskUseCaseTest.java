package usecases;

import com.marti.application.dtos.task.UpdateTaskRequest;
import com.marti.application.usecases.task.UpdateTaskUseCase;
import com.marti.domain.model.Priority;
import com.marti.domain.model.Task;
import com.marti.domain.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateTaskUseCaseTest {

    @Mock
    private TaskRepository taskRepo;

    private UpdateTaskUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new UpdateTaskUseCase(taskRepo);
    }

    @Test
    void shouldUpdateTaskSuccessfully() {
        Task task = mock(Task.class);
        when(task.getTaskListId()).thenReturn("list1");
        when(taskRepo.findById("task1")).thenReturn(Optional.of(task));

        UpdateTaskRequest request = new UpdateTaskRequest(
                "list1",
                "task1",
                "New title",
                "New description",
                "HIGH",
                null
        );

        useCase.execute(request);

        verify(task).update("New title", "New description", Priority.HIGH, null);
        verify(taskRepo).save(task);
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFound() {
        when(taskRepo.findById("task1")).thenReturn(Optional.empty());

        UpdateTaskRequest request = mock(UpdateTaskRequest.class);
        when(request.getTaskId()).thenReturn("task1");

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(request));
    }
}
