package usecases;

import com.marti.application.usecases.task.ViewTasksUseCase;
import com.marti.domain.model.Task;
import com.marti.domain.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ViewTasksUseCaseTest {

    @Mock
    private TaskRepository taskRepo;

    private ViewTasksUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new ViewTasksUseCase(taskRepo);
    }

    @Test
    void shouldReturnTasksForTaskList() {
        Task task = mock(Task.class);
        when(taskRepo.findByTaskListId("list1")).thenReturn(List.of(task));

        var result = useCase.execute("list1");

        assertEquals(1, result.size());
    }

    @Test
    void shouldThrowExceptionWhenTaskListIdIsEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(""));

        verify(taskRepo, never()).findByTaskListId(any());
    }
}
