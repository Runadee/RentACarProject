package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private Connection connection = null;
    private static Database instance = null;

    private Database() {
        try {
             this.connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/rentacar",
                    "postgres",
                    "9252"
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static Connection getInstance()  {

        try {
            if (instance == null || instance.getConnection().isClosed()) {

                instance = new Database();
                return instance.getConnection();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return instance.getConnection();
    }
}
