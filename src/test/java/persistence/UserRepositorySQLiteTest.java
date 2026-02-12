package persistence;

import com.marti.domain.model.User;
import com.marti.infrastructure.persistence.DatabaseConnection;
import com.marti.infrastructure.persistence.UserRepositorySQLite;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositorySQLiteTest {

    private UserRepositorySQLite repository;

    @BeforeEach
    void setUp() throws Exception {

        DatabaseConnection.setUrl("jdbc:sqlite:./src/test/java/persistence/test.db");

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("DROP TABLE IF EXISTS users;");

            stmt.execute("""
                CREATE TABLE users (
                    id TEXT PRIMARY KEY,
                    username TEXT NOT NULL,
                    email TEXT NOT NULL UNIQUE,
                    passwordHash TEXT NOT NULL
                );
            """);
        }

        repository = new UserRepositorySQLite();
    }

    private User createSampleUser(String id) {
        return User.reconstruct(
                id,
                "marti",
                "marti@test.com",
                "hashedpassword"
        );
    }

    @Test
    void shouldSaveAndFindById() {
        User user = createSampleUser("1");

        repository.save(user);

        Optional<User> result = repository.findById("1");

        assertTrue(result.isPresent());
        assertEquals("marti", result.get().getUsername());
    }

    @Test
    void shouldReturnEmptyWhenUserNotExists() {
        Optional<User> result = repository.findById("999");

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldDeleteUser() {
        User user = createSampleUser("2");

        repository.save(user);
        repository.deleteById("2");

        Optional<User> result = repository.findById("2");

        assertTrue(result.isEmpty());
    }
}
