package persistence;

import com.marti.domain.model.Priority;
import com.marti.domain.model.Status;
import com.marti.domain.model.Task;
import com.marti.infrastructure.persistence.DatabaseConnection;
import com.marti.infrastructure.persistence.TaskRepositorySQLite;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TaskRepositorySQLiteTest {

    private TaskRepositorySQLite repository;

    @BeforeEach
    void setUp() throws Exception {


        DatabaseConnection.setUrl("jdbc:sqlite:./src/test/java/persistence/test.db");

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS tasks;");
            stmt.execute("DROP TABLE IF EXISTS task_lists;");
            stmt.execute("DROP TABLE IF EXISTS users;");

            stmt.execute("""
                CREATE TABLE users (
                    id TEXT PRIMARY KEY,
                    username TEXT NOT NULL,
                    email TEXT NOT NULL UNIQUE,
                    passwordHash TEXT NOT NULL
                );
            """);

            stmt.execute("""
                CREATE TABLE task_lists (
                    id TEXT PRIMARY KEY,
                    name TEXT NOT NULL,
                    userId TEXT NOT NULL
                );
            """);

            stmt.execute("""
                CREATE TABLE tasks (
                    id TEXT PRIMARY KEY,
                    taskListId TEXT NOT NULL,
                    title TEXT NOT NULL,
                    description TEXT,
                    status TEXT NOT NULL,
                    priority TEXT NOT NULL,
                    dueDate INTEGER,
                    createdAt INTEGER NOT NULL,
                    updatedAt INTEGER NOT NULL,
                    userId TEXT NOT NULL
                );
            """);
        }

        repository = new TaskRepositorySQLite();
    }

    private Task createSampleTask(String id) {
        return Task.reconstruct(
                id,
                "list1",
                "Test Task",
                "Description",
                Status.TODO,
                Priority.MEDIUM,
                null,
                new Date(),
                new Date(),
                "user1"
        );
    }

    @Test
    void shouldSaveAndFindById() {
        Task task = createSampleTask("1");

        repository.save(task);

        Optional<Task> result = repository.findById("1");

        assertTrue(result.isPresent());
        assertEquals("Test Task", result.get().getTitle());
    }

    @Test
    void shouldReturnEmptyWhenTaskNotExists() {
        Optional<Task> result = repository.findById("999");

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldFindByTaskListId() {
        Task task = createSampleTask("2");

        repository.save(task);

        List<Task> tasks = repository.findByTaskListId("list1");

        assertEquals(1, tasks.size());
    }

    @Test
    void shouldDeleteTask() {
        Task task = createSampleTask("3");

        repository.save(task);
        repository.deleteById("3");

        Optional<Task> result = repository.findById("3");

        assertTrue(result.isEmpty());
    }
}
