package ru.protei.proxy.database;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {

    private static final Logger log = Logger.getLogger(DBConnectionManager.class);

    private static final String JDBC_DB_URL = "jdbc:h2:mem:";
    private static final String JDBC_USER = "sa";
    private static final String JDBC_PASSWORD = "";

    private Connection connection;
    private static DBConnectionManager instance;

    private DBConnectionManager() {
    }

    public static DBConnectionManager getInstance() {
        if (instance == null) {
            instance = new DBConnectionManager();
        }
        return instance;
    }

    public Connection getConnection() {
        if (connection == null) {
            try {
                log.info("Trying to connect to database...");
                connection = DriverManager.getConnection(JDBC_DB_URL, JDBC_USER, JDBC_PASSWORD);
                log.info("Connection to database established successfully");
            } catch (SQLException e) {
                log.fatal("Failed to connect to database", e);
            }
        }
        return connection;
    }
}
