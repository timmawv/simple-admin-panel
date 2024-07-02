package avlyakulov.timur.service;

import avlyakulov.timur.dao.UserDao;
import avlyakulov.timur.entity.User;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    public List<User> findAll() {
        return userDao.findAll();
    }
}