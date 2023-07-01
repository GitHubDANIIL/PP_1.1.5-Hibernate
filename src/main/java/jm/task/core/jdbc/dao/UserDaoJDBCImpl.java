package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import static jm.task.core.jdbc.util.Util.getConnectionToDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    private static Connection connection = getConnectionToDB();

    public void createUsersTable() {

        String createTable = "CREATE TABLE IF NOT EXISTS users(id INT PRIMARY KEY AUTO_INCREMENT NOT NULL," +
                " name VARCHAR(45) NOT NULL," +
                " lastname VARCHAR(45) NOT NULL," +
                " age TINYINT(10) NOT NULL)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(createTable)) {
            preparedStatement.executeUpdate(createTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void dropUsersTable() {

        String dropTable = "DROP TABLE IF EXISTS users";

        try (PreparedStatement preparedStatement = connection.prepareStatement(dropTable)) {
            preparedStatement.executeUpdate(dropTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {

        User user = new User(name, lastName, age);
        String save = "INSERT users(name, lastname, age) VALUES(?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(save)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setByte(3, user.getAge());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {

        String remove = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(remove)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();
        String get = "SELECT * FROM users";

        try (PreparedStatement preparedStatement = connection.prepareStatement(get)) {
            ResultSet resultSet = preparedStatement.executeQuery(get);

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

        String clean = "TRUNCATE users";

        try (PreparedStatement preparedStatement = connection.prepareStatement(clean)) {
            preparedStatement.executeUpdate(clean);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
