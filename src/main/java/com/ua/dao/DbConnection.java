package com.ua.dao;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface DbConnection {
    /**
     * Create connection to database
     * @return connection to database
     * @throws SQLException when cannot create connection
     */
    Connection getConnection() throws SQLException;
}
