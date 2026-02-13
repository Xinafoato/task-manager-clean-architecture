package com.marti.infrastructure.persistence;

import com.marti.domain.model.Priority;
import com.marti.domain.model.Status;
import com.marti.domain.model.Task;
import com.marti.domain.model.TaskList;
import com.marti.domain.repository.TaskListRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskListRepositorySQLite implements TaskListRepository {

    @Override
    public TaskList save(TaskList taskList) {

        String sql = "INSERT INTO task_lists (id, name, userId) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, taskList.getId());
            stmt.setString(2, taskList.getName());
            stmt.setString(3, taskList.getUserId());

            stmt.executeUpdate();

            return taskList;

        } catch (Exception e) {
            throw new RuntimeException("Error saving task list", e);
        }
    }

    @Override
    public Optional<TaskList> findById(String taskListId) {

        String sql = "SELECT id, name, userId FROM task_lists WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, taskListId);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                return Optional.empty();
            }

            String id = rs.getString("id");
            String name = rs.getString("name");
            String userId = rs.getString("userId");

            // ORM para cargar las tasks
            List<Task> tasks = loadTasksByTaskListId(conn, id);

            TaskList taskList = TaskList.reconstruct(id, name, userId, tasks);

            return Optional.of(taskList);

        } catch (Exception e) {
            throw new RuntimeException("Error finding task list by id", e);
        }
    }

    @Override
    public List<TaskList> findByUserId(String userId) {

        String sql = "SELECT id, name, userId FROM task_lists WHERE userId = ?";

        List<TaskList> taskLists = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                String id = rs.getString("id");
                String name = rs.getString("name");

                List<Task> tasks = loadTasksByTaskListId(conn, id);

                TaskList taskList = TaskList.reconstruct(id, name, userId, tasks);

                taskLists.add(taskList);
            }

            return taskLists;

        } catch (Exception e) {
            throw new RuntimeException("Error finding task lists by user id", e);
        }
    }

    @Override
    public boolean existsByNameAndUserId(String name, String userId) {

        String sql = "SELECT 1 FROM task_lists WHERE name = ? AND userId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, userId);

            ResultSet rs = stmt.executeQuery();

            return rs.next();

        } catch (Exception e) {
            throw new RuntimeException("Error checking if task list exists", e);
        }
    }

    @Override
    public void deleteById(String taskListId) {

        String sql = "DELETE FROM task_lists WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, taskListId);

            int rows = stmt.executeUpdate();

            if (rows == 0) {
                throw new IllegalArgumentException("Task list not found with id: " + taskListId);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error deleting task list", e);
        }
    }

    @Override
    public void updateName(String taskListId, String newName) {

        String sql = "UPDATE task_lists SET name = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newName);
            stmt.setString(2, taskListId);

            int rows = stmt.executeUpdate();

            if (rows == 0) {
                throw new IllegalArgumentException("Task list not found with id: " + taskListId);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error updating task list name", e);
        }
    }

    // Metodo para cargar tasks
    private List<Task> loadTasksByTaskListId(Connection conn, String taskListId) throws SQLException {

        String sql = """
               
                SELECT id, taskListId, title, description, status, priority, dueDate, createdAt, updatedAt, userId 
                FROM tasks
                WHERE taskListId = ?
                """;

        List<Task> tasks = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, taskListId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Task task = Task.reconstruct(
                        rs.getString("id"),
                        rs.getString("taskListId"),
                        rs.getString("title"),
                        rs.getString("description"),
                        Status.valueOf(rs.getString("status")),
                        Priority.valueOf(rs.getString("priority")),
                        rs.getTimestamp("dueDate") != null ? new Date(rs.getTimestamp("dueDate").getTime()) : null,
                        new Date(rs.getTimestamp("createdAt").getTime()),
                        new Date(rs.getTimestamp("updatedAt").getTime()),
                        rs.getString("userId")

                );

                tasks.add(task);
            }
        }

        return tasks;
    }


}
