package org.youcode.easybank_hibernate.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection conn;
    private static DBConnection instance;

    private DBConnection() {
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection establishConnection() {
        if (conn == null) {
            String jdbcUrl = "jdbc:postgresql://localhost:5432/easybankHib";
            String username = "postgres";
            String password = "1999";

            try {
                Class.forName("org.postgresql.Driver");
                conn = DriverManager.getConnection(jdbcUrl, username, password);

                if (conn != null) {
                    System.out.println("Connection to PostgreSQL database established.");
                }else
                {
                    System.out.println("no connecion !");
                }

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
