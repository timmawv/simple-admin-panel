package avlyakulov.timur.dao;

import avlyakulov.timur.config.db.DataSource;
import avlyakulov.timur.entity.User;
import avlyakulov.timur.exception.AuthException;
import avlyakulov.timur.exception.UserAlreadyExistException;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserDao {

    public void create(String login, String password) {
        String query = "insert into users (login, password, role) values (?, ?, 'USER')";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
           statement.setString(1, login);
           statement.setString(2, password);

           statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error with connection to db");
            throw new UserAlreadyExistException("user already exists with this login");
        }
    }

    public List<User> findAll() {
        String query = "select * from users";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(setUser(resultSet));
            }
            return users;
        } catch (SQLException e) {
            log.error("Error with connection to db");
            throw new RuntimeException(e.getMessage());
        }
    }

    public void findById(Integer id) {

    }

    public User findByLogin(String login) {
        String query = "select * from users where login = ?";
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return setUser(resultSet);
            throw new AuthException("Login or password isn't correct");
        } catch (SQLException e) {
            log.error("Error with connection to db");
            throw new RuntimeException(e.getMessage());
        }
    }

    private User setUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("id"),
                resultSet.getString("login"),
                resultSet.getString("password"),
                resultSet.getString("role")
        );
    }
}