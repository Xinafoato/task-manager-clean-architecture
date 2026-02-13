package com.marti.infrastructure.persistence;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void init() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // Tabla de usuarios
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS users (
                    id TEXT PRIMARY KEY,
                    username TEXT NOT NULL,
                    email TEXT NOT NULL UNIQUE,
                    passwordHash TEXT NOT NULL
                );
            """);

            // Tabla de listas de tareas
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS task_lists (
                    id TEXT PRIMARY KEY,
                    name TEXT NOT NULL,
                    userId TEXT NOT NULL,
                    FOREIGN KEY(userId) REFERENCES users(id)
                );
            """);

            // Tabla de tareas
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS tasks (
                    id TEXT PRIMARY KEY,
                    taskListId TEXT NOT NULL,
                    title TEXT NOT NULL,
                    description TEXT,
                    status TEXT NOT NULL,
                    priority TEXT NOT NULL,
                    dueDate INTEGER,
                    createdAt INTEGER NOT NULL,
                    updatedAt INTEGER NOT NULL,
                    userId TEXT NOT NULL,
                    FOREIGN KEY(userId) REFERENCES users(id),
                    FOREIGN KEY(taskListId) REFERENCES task_lists(id)
                );
            """);

            System.out.println("Database initialized successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
