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

        String sql = "INSERT INTO task_lists (id, name, user_id) VALUES (?, ?, ?)";

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

        String sql = "SELECT id, name, user_id FROM task_lists WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, taskListId);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                return Optional.empty();
            }

            String id = rs.getString("id");
            String name = rs.getString("name");
            String userId = rs.getString("user_id");

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

        String sql = "SELECT id, name, user_id FROM task_lists WHERE user_id = ?";

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

        String sql = "SELECT 1 FROM task_lists WHERE name = ? AND user_id = ?";

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

    // Metodo para cargar tasks
    private List<Task> loadTasksByTaskListId(Connection conn, String taskListId) throws SQLException {

        String sql = """
               
                SELECT id, title, description, status, priority, due_date, created_at, updated_at, user_id, task_list_id
                FROM tasks
                WHERE task_list_id = ?
                """;

        List<Task> tasks = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, taskListId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Task task = Task.reconstruct(
                        rs.getString("id"),
                        rs.getString("task_list_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        Status.valueOf(rs.getString("status")),
                        Priority.valueOf(rs.getString("priority")),
                        rs.getTimestamp("due_date") != null ? new Date(rs.getTimestamp("due_date").getTime()) : null,
                        new Date(rs.getTimestamp("created_at").getTime()),
                        new Date(rs.getTimestamp("updated_at").getTime()),
                        rs.getString("user_id")

                );

                tasks.add(task);
            }
        }

        return tasks;
    }
}
