package avlyakulov.timur.service;

import avlyakulov.timur.dao.UserDao;
import avlyakulov.timur.dto.UserDto;
import avlyakulov.timur.dto.UserSession;
import avlyakulov.timur.dto.UserUpdateDto;
import avlyakulov.timur.entity.User;
import avlyakulov.timur.exception.AuthException;
import avlyakulov.timur.mapper.UserMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    private final UserMapper userMapper = UserMapper.INSTANCE;

    public void createUser(UserDto userDto) {
        userDao.create(userDto.getLogin(), userDto.getPassword());
    }

    public void updateUser(UserUpdateDto userUpdateDto) {
        User user = userMapper.toUser(userUpdateDto);
        userDao.updateUser(user);
    }

    public void deleteUser(Integer userId) {
        userDao.deleteUser(userId);
    }

    public UserSession loginUser(UserDto userDto) throws AuthException {
        User user = userDao.findByLogin(userDto.getLogin());

        if (!user.getPassword().equals(userDto.getPassword()))
            throw new AuthException("Login or password isn't correct");

        return new UserSession(user.getLogin(), user.getRole());
    }

    public List<User> giveInformationByUserLogin(String login) {
        User user = userDao.findByLogin(login);

        switch (user.getRole()) {
            case "USER" -> {
                return List.of(user);
            }
            case "ADMIN" -> {
                return userDao.findAll().stream()
                        .filter(u -> u.getRole().equals("USER"))
                        .toList();
            }
            default -> throw new RuntimeException("User has a wrong role");
        }
    }
}