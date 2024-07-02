package avlyakulov.timur.service;

import avlyakulov.timur.dao.UserDao;
import avlyakulov.timur.dto.UserDto;
import avlyakulov.timur.entity.User;
import avlyakulov.timur.exception.AuthException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    public List<User> findAll() {
        return userDao.findAll();
    }

    public void loginUser(UserDto userDto) throws AuthException {
        User user = userDao.findByLogin(userDto.getLogin());

        if (!user.getPassword().equals(userDto.getPassword()))
            throw new AuthException("Login or password isn't correct");
    }
}