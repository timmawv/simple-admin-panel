package avlyakulov.timur.dao;

import avlyakulov.timur.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    public void create() {

    }

    public void findAll() {

    }

    public void findById(Integer id) {

    }

    private User setCurrency(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("id"),
                resultSet.getString("login"),
                resultSet.getString("password"),
                resultSet.getString("role")
        );
    }
}