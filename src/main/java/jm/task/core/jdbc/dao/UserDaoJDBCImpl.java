package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.net.ConnectException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Connection connection;

    {
        try {
            connection = Util.getConnection();
        } catch (ConnectException e) {
            System.out.println("Connection is failed!!!");
        }
    }

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                    "id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(45) NOT NULL," +
                    "lastname VARCHAR(45) NOT NULL," +
                    "age TINYINT NOT NULL);");

//            System.out.println("Table was created.");

        } catch (SQLException e) {
            System.out.println("Some error was occurred while creating a table!!!");
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {

        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate("DROP TABLE IF EXISTS users;");

//            System.out.println("Table was dropped");

        } catch (SQLException e) {
            System.out.println("Some error was occurred while dropping the table!!!");
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {

        final String SAVE = "INSERT INTO users (name, lastname, age) VALUES (?,?,?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);

            preparedStatement.executeUpdate();

            System.out.println("User with the name - " + name +
                    " was added to database");

        } catch (SQLException e) {
            System.out.println("Some error was occurred while saving a user!!!");
            e.printStackTrace();
        }

    }

    public void removeUserById(long id) {

        final String DELETE = "DELETE FROM users WHERE id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();

//            System.out.println("User with id " + id + " was removed from database");

        } catch (SQLException e) {
            System.out.println("Some error was occurred while removing the user!!!");
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM users;");

            while (resultSet.next()) {

                User user = new User();

                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));

                users.add(user);

            }

        } catch (SQLException e) {
            System.out.println("Some error was occurred while getting all users!!!");
            e.printStackTrace();
        }

        return users;

    }

    public void cleanUsersTable() {

        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate("TRUNCATE TABLE users;");

//            System.out.println("Table was cleaned");

        } catch (SQLException e) {
            System.out.println("Some error was occurred while cleaning the table!!!");
            e.printStackTrace();
        }

    }
}
