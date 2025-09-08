package de.afp.db_kino.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfig {

    public Connection connection;

    public void initDatabaseConnection() throws SQLException {
        System.out.println("Connecting to Database ...");
        connection = DriverManager.getConnection(
                "jdbc:mariadb://127.0.0.1:3306/cinema_obskura", "root", "");
        System.out.println("Connection established " + connection.isValid(5));
    }

    public void closeDatabaseConnection() throws SQLException {
        System.out.println("Closing Database Connection ...");
        connection.close();
        System.out.println("Connection valid " + connection.isClosed());
    }

}
