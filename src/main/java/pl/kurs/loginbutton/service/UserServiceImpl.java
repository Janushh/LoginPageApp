package pl.kurs.loginbutton.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kurs.loginbutton.dto.UserCredentialsDTO;
import pl.kurs.loginbutton.repository.UserRepository;
import pl.kurs.loginbutton.user.User;
import pl.kurs.loginbutton.user.UserDTO;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public UserDTO addUser(UserCredentialsDTO userCredentialsDTO) {
        String encodedPassword = passwordEncoder.encode(userCredentialsDTO.getPassword());
        User user = new User(null, userCredentialsDTO.getLogin(), encodedPassword);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public Optional<UserDTO> findUserByLogin(String login) {
        return userRepository.findByLogin(login)
                .map(user -> modelMapper.map(user, UserDTO.class));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .toList();
    }

    @Override
    public boolean validateLogin(UserCredentialsDTO credentials) {
        return findUserByLogin(credentials.getLogin())
                .map(user -> passwordEncoder.matches(credentials.getPassword(), user.getPassword()))
                .orElse(false);
    }

}