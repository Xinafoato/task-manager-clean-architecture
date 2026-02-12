package usecases;

import com.marti.application.usecases.task.DeleteTaskUseCase;
import com.marti.domain.model.Task;
import com.marti.domain.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteTaskUseCaseTest {

    @Mock
    private TaskRepository taskRepo;

    private DeleteTaskUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new DeleteTaskUseCase(taskRepo);
    }

    @Test
    void shouldDeleteTaskWhenTaskExistsAndBelongsToList() {
        Task task = mock(Task.class);
        when(task.getTaskListId()).thenReturn("list1");
        when(taskRepo.findById("task1")).thenReturn(Optional.of(task));

        useCase.execute("list1", "task1");

        verify(taskRepo).deleteById("task1");
    }

    @Test
    void shouldThrowExceptionWhenTaskDoesNotBelongToList() {
        Task task = mock(Task.class);
        when(task.getTaskListId()).thenReturn("otherList");
        when(taskRepo.findById("task1")).thenReturn(Optional.of(task));

        assertThrows(IllegalArgumentException.class, () -> useCase.execute("list1", "task1"));

        verify(taskRepo, never()).deleteById(any());
    }
}
