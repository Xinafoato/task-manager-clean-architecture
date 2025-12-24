

import com.marti.application.usecases.task.AddTagToTaskUseCase;
import com.marti.domain.service.DomainController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddTagToTaskUseCaseTest {

    @Mock
    private DomainController domainController;

    private AddTagToTaskUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new AddTagToTaskUseCase(domainController);
    }

    @Test
    void shouldAddTagSuccessfully() {
        useCase.execute("list1", "task1", "urgent");

        verify(domainController)
                .addTagToTask("list1", "task1", "urgent");
    }

    @Test
    void shouldThrowExceptionWhenTagIsEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> useCase.execute("list1", "task1", ""));
    }
}
