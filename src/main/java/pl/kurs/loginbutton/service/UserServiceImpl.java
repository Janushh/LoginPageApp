package pl.kurs.loginbutton.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kurs.loginbutton.repository.UserRepository;
import pl.kurs.loginbutton.user.User;
import pl.kurs.loginbutton.user.UserDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO addUser(String login, String rawPassword) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User(null, login, encodedPassword);
        User savedUser = userRepository.save(user);
        return mapToDTO(savedUser);
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean validateLogin(String login, String rawPassword) {
        return findUserByLogin(login)
                .map(user -> passwordEncoder.matches(rawPassword, user.getPassword()))
                .orElse(false);
    }

    private UserDTO mapToDTO(User user) {
        return new UserDTO(user.getId(), user.getLogin());
    }
}