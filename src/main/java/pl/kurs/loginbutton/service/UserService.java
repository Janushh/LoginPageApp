package pl.kurs.loginbutton.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kurs.loginbutton.repository.UserRepository;
import pl.kurs.loginbutton.user.User;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User addUser(String login, String rawPassword) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User(null, login, encodedPassword);
        return (User) userRepository.save(user);
    }

    public Optional<User> findUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean validateLogin(String login, String rawPassword) {
        return findUserByLogin(login)
                .map(user -> passwordEncoder.matches(rawPassword, user.getPassword()))
                .orElse(false);
    }
}
