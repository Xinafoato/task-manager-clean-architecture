package com.marti.infrastructure.persistence;

import com.marti.domain.model.User;
import com.marti.domain.repository.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class UserRepositorySQLite implements UserRepository {

    @Override
    public User  save(User user) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String insertSql = "INSERT INTO users (id, username, email, passwordHash) VALUES (?, ?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, user.getId());
                insertStmt.setString(2, user.getUsername());
                insertStmt.setString(3, user.getEmail());
                insertStmt.setString(4, user.getPasswordHash());
                insertStmt.executeUpdate();
            }

            String selectSql = "SELECT id, username, email, passwordHash FROM users WHERE id = ?";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                selectStmt.setString(1, user.getId());
                var rs = selectStmt.executeQuery();

                if (rs.next()) {
                    return User.reconstruct(
                            rs.getString("id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("passwordHash")
                    );
                } else {
                    throw new RuntimeException("Failed to retrieve user after insertion");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error saving user in SQLite", e);
        }
    }

    @Override
    public Optional<User> findById(String userId) {
        try (Connection conn = DatabaseConnection.getConnection()){
            String sql = "SELECT id, username, email, passwordHash FROM users WHERE id = ?";
            try(PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1,userId);
                var rs = stmt.executeQuery();
                if(rs.next()) {
                    User user = User.reconstruct(
                            rs.getString("id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("passwordHash")
                    );
                    return  Optional.of(user);
                }else {
                    return Optional.empty();
                }
            }

        }catch (Exception e) {
            throw new RuntimeException("Error finding user by ID", e);
        }

    }

    @Override
    public Optional<User> findByEmail(String email) {

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT id, username, email, passwordHash FROM users WHERE email = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, email);
                var rs = stmt.executeQuery();
                if (rs.next()) {
                    User user = User.reconstruct(
                            rs.getString("id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("passwordHash")
                    );
                    return Optional.of(user);
                } else {
                    return Optional.empty();
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error finding user by email", e);

        }
    }


    @Override
    public boolean existsByEmail(String email) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT 1 FROM users WHERE email = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, email);
                var rs = stmt.executeQuery();
                return rs.next();
            }

        } catch (Exception e) {
            throw new RuntimeException("Error checking if email exists",e);
        }
    }

    @Override
    public void deleteById(String userId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM users WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, userId);

                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected == 0) {
                    throw new IllegalArgumentException("User not found with id: " + userId);
                }
            }
        }catch (Exception e) {
            throw new RuntimeException("Error deleting user", e);
        }
    }
}
