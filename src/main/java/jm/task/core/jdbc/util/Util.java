package jm.task.core.jdbc.util;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/katadatabase";
    private static final String USERNAME = "some";
    private static final String PASSWORD = "some";

    public static Connection getConnection() throws ConnectException {
        try {
//            System.out.println("Connection is open");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
//            System.out.println("Connection error");
            throw new ConnectException();
        }
    }

}