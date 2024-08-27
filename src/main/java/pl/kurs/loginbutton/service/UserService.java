package pl.kurs.loginbutton.service;


import pl.kurs.loginbutton.dto.UserCredentialsDTO;
import pl.kurs.loginbutton.user.UserDTO;

import java.util.List;
import java.util.Optional;


public interface UserService {
    UserDTO addUser(UserCredentialsDTO credentialsDTO);
    Optional<UserDTO> findUserByLogin(String login);
    List<UserDTO> getAllUsers();
    boolean validateLogin(UserCredentialsDTO credentialsDTO);
}
