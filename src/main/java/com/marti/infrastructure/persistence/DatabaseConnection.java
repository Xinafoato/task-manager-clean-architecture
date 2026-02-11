package com.marti.infrastructure.persistence;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:./src/main/resources/taskmanager.db";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL);
    }
}
