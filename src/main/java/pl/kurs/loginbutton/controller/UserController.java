package pl.kurs.loginbutton.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.loginbutton.dto.UserCredentialsDTO;
import pl.kurs.loginbutton.service.UserService;
import pl.kurs.loginbutton.user.User;
import pl.kurs.loginbutton.user.UserDTO;

import java.util.List;



@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserCredentialsDTO credentials) {
        String login = credentials.getLogin();
        String password = credentials.getPassword();

        if (userService.validateLogin(login, password)) {
            return ResponseEntity.ok("okej!");
        } else {
            return ResponseEntity.status(401).body("źle!!!");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserCredentialsDTO credentials) {
        String login = credentials.getLogin();
        String password = credentials.getPassword();
        UserDTO userDTO = userService.addUser(login, password);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}