package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import static jm.task.core.jdbc.util.Util.getConnectionToDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Statement statement;
        try (Connection connection = getConnectionToDB()) {
            statement = connection.createStatement();
            String createTable = "CREATE TABLE IF NOT EXISTS users(id INT PRIMARY KEY AUTO_INCREMENT NOT NULL," +
                    " name VARCHAR(45) NOT NULL," +
                    " lastname VARCHAR(45) NOT NULL," +
                    " age TINYINT(10) NOT NULL)";
            statement.executeUpdate(createTable);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void dropUsersTable() {
        Statement statement;
        try (Connection connection = getConnectionToDB()) {
            statement = connection.createStatement();
            String dropTable = "DROP TABLE IF EXISTS users";
            statement.executeUpdate(dropTable);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Connection connection = getConnectionToDB()) {
            String save = "INSERT users(name, lastname, age) VALUES(?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(save);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setByte(3, user.getAge());
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = getConnectionToDB()) {
            String remove = "DELETE FROM users WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(remove);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {

        Statement statement;
        List<User> users = new ArrayList<>();

        try (Connection connection = getConnectionToDB()) {
            statement = connection.createStatement();
            String get = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(get);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));

                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return users;
    }

    public void cleanUsersTable() {

        Statement statement;

        try(Connection connection = getConnectionToDB()) {
        statement = connection.createStatement();
        String clean = "DELETE FROM users";
        statement.executeUpdate(clean);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
