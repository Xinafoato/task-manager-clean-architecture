package persistence;

import com.marti.domain.model.TaskList;
import com.marti.infrastructure.persistence.DatabaseConnection;
import com.marti.infrastructure.persistence.TaskListRepositorySQLite;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TaskListRepositorySQLiteTest {

    private TaskListRepositorySQLite repository;

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

        repository = new TaskListRepositorySQLite();
    }

    private TaskList createSampleTaskList(String id) {
        return TaskList.reconstruct(
                id,
                "My List",
                "user1",
                new ArrayList<>()
        );
    }

    @Test
    void shouldSaveAndFindById() {
        TaskList list = createSampleTaskList("1");

        repository.save(list);

        Optional<TaskList> result = repository.findById("1");

        assertTrue(result.isPresent());
        assertEquals("My List", result.get().getName());
    }

    @Test
    void shouldReturnEmptyWhenListNotExists() {
        Optional<TaskList> result = repository.findById("999");

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldFindByUserId() {
        TaskList list1 = createSampleTaskList("1");
        TaskList list = createSampleTaskList("2");

        repository.save(list1);
        repository.save(list);


        List<TaskList> lists = repository.findByUserId("user1");

        assertEquals(2, lists.size());
    }

    @Test
    void shouldDeleteTaskList() {
        TaskList list = createSampleTaskList("3");

        repository.save(list);
        repository.deleteById("3");

        Optional<TaskList> result = repository.findById("3");

        assertTrue(result.isEmpty());
    }
}
