package com.marti.infrastructure.persistence;

import com.marti.domain.model.Priority;
import com.marti.domain.model.Status;
import com.marti.domain.model.Task;
import com.marti.domain.repository.TaskRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TaskRepositorySQLite implements TaskRepository {

    @Override
    public void save(Task task) {
        String sql = """
            INSERT OR REPLACE INTO tasks 
            (id, taskListId, title, description, status, priority, dueDate, createdAt, updatedAt, userId)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, task.getId());
            stmt.setString(2, task.getTaskListId());
            stmt.setString(3, task.getTitle());
            stmt.setString(4, task.getDescription());
            stmt.setString(5, task.getStatus().name());
            stmt.setString(6, task.getPriority().name());
            stmt.setLong(7, task.getDueDate() != null ? task.getDueDate().getTime() : 0L);
            stmt.setLong(8, task.getCreatedAt().getTime());
            stmt.setLong(9, task.getUpdatedAt().getTime());
            stmt.setString(10, task.getUserId());

            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Error saving task in SQLite", e);
        }
    }

    @Override
    public Optional<Task> findById(String taskId) {
        String sql = """
            SELECT * FROM tasks WHERE id = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, taskId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Task task = reconstructTaskFromResultSet(rs);
                return Optional.of(task);
            } else {
                return Optional.empty();
            }

        } catch (Exception e) {
            throw new RuntimeException("Error finding task by ID", e);
        }
    }

    @Override
    public List<Task> findByTaskListId(String taskListId) {
        String sql = """
            SELECT * FROM tasks WHERE taskListId = ?
            """;

        List<Task> tasks = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, taskListId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tasks.add(reconstructTaskFromResultSet(rs));
            }

        } catch (Exception e) {
            throw new RuntimeException("Error finding tasks by TaskListId", e);
        }

        return tasks;
    }

    @Override
    public List<Task> findByUserId(String userId) {
        String sql = """
            SELECT * FROM tasks WHERE userId = ?
            """;

        List<Task> tasks = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tasks.add(reconstructTaskFromResultSet(rs));
            }

        } catch (Exception e) {
            throw new RuntimeException("Error finding tasks by UserId", e);
        }

        return tasks;
    }

    @Override
    public void deleteById(String taskId) {
        String sql = """
            DELETE FROM tasks WHERE id = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, taskId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new IllegalArgumentException("Task not found with id: " + taskId);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error deleting task", e);
        }
    }


    // Helper: reconstruct Task from ResultSet
    private Task reconstructTaskFromResultSet(ResultSet rs) throws Exception {
        return Task.reconstruct(
                rs.getString("id"),
                rs.getString("taskListId"),
                rs.getString("title"),
                rs.getString("description"),
                Status.valueOf(rs.getString("status")),
                Priority.valueOf(rs.getString("priority")),
                rs.getLong("dueDate") != 0 ? new Date(rs.getLong("dueDate")) : null,
                new Date(rs.getLong("createdAt")),
                new Date(rs.getLong("updatedAt")),
                rs.getString("userId")
        );
    }
}
