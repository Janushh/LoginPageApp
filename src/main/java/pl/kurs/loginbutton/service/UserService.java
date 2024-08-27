package pl.kurs.loginbutton.service;


import pl.kurs.loginbutton.user.User;
import pl.kurs.loginbutton.user.UserDTO;

import java.util.List;
import java.util.Optional;


public interface UserService {
    UserDTO addUser(String login, String rawPassword);
    Optional<User> findUserByLogin(String login);
    List<UserDTO> getAllUsers();
    boolean validateLogin(String login, String rawPassword);
}
