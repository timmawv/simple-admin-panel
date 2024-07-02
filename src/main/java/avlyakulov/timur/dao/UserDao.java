package avlyakulov.timur.dao;

import avlyakulov.timur.config.db.DataSource;
import avlyakulov.timur.entity.User;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserDao {

    public void create() {

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

    private User setUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("id"),
                resultSet.getString("login"),
                resultSet.getString("password"),
                resultSet.getString("role")
        );
    }
}