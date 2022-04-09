package com.ua.dao;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class PostgresDbConnection implements DbConnection {

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/simplemvcdb";
        String username = "postgres";
        String password = "root";
        return DriverManager.getConnection(url, username, password);
    }
}
