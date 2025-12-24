

import com.marti.application.usecases.task.StartTaskUseCase;
import com.marti.domain.model.Task;
import com.marti.domain.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StartTaskUseCaseTest {

    @Mock
    private TaskRepository taskRepo;

    private StartTaskUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new StartTaskUseCase(taskRepo);
    }

    @Test
    void shouldStartTaskSuccessfully() {
        Task task = mock(Task.class);
        when(task.getTaskListId()).thenReturn("list1");
        when(taskRepo.findById("task1")).thenReturn(Optional.of(task));

        useCase.execute("list1", "task1");

        verify(task).start();
        verify(taskRepo).save(task);
    }

    @Test
    void shouldThrowExceptionIfTaskNotFound() {
        when(taskRepo.findById("task1")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> useCase.execute("list1", "task1"));

        verify(taskRepo, never()).save(any());
    }
}
