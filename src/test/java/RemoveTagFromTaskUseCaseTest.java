

import com.marti.application.usecases.task.RemoveTagFromTaskUseCase;
import com.marti.domain.service.DomainController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RemoveTagFromTaskUseCaseTest {

    @Mock
    private DomainController domainController;

    private RemoveTagFromTaskUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new RemoveTagFromTaskUseCase(domainController);
    }

    @Test
    void shouldRemoveTagSuccessfully() {
        useCase.execute("list1", "task1", "urgent");

        verify(domainController)
                .removeTagFromTask("list1", "task1", "urgent");
    }

    @Test
    void shouldThrowExceptionWhenTaskIdIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> useCase.execute("list1", null, "urgent"));
    }
}
