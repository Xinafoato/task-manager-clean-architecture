

import com.marti.application.dtos.task.AddTaskRequest;
import com.marti.application.dtos.task.TaskDTO;
import com.marti.application.usecases.task.AddTaskUseCase;
import com.marti.domain.repository.TaskRepository;
import com.marti.domain.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddTaskUseCaseTest {

    @Mock
    private TaskRepository taskRepo;

    private AddTaskUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new AddTaskUseCase(taskRepo);
    }

    @Test
    void shouldAddTaskSuccessfully() {
        AddTaskRequest request = new AddTaskRequest(
                "list1",
                "Title",
                "Description",
                "TODO",
                new Date(2025, 0, 1),
                null,
                "user1"
        );

        TaskDTO result = useCase.execute(request);

        assertNotNull(result);
        assertEquals("Title", result.getTitle());
        verify(taskRepo, times(1)).save(any(Task.class));
    }

    @Test
    void shouldThrowExceptionWhenRequestIsNull() {
        assertThrows(IllegalArgumentException.class, () -> useCase.execute(null));
        verify(taskRepo, never()).save(any());
    }
}
