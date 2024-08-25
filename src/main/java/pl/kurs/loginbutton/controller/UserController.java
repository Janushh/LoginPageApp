package pl.kurs.loginbutton.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.loginbutton.service.UserService;
import pl.kurs.loginbutton.user.User;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {
        String login = credentials.get("login");
        String password = credentials.get("password");

        if (userService.validateLogin(login, password)) {
            return ResponseEntity.ok("okej!");
        } else {
            return ResponseEntity.status(401).body("źle!!!");
        }
    }

    @PostMapping("/register")
    public User register(@RequestBody Map<String, String> credentials) {
        String login = credentials.get("login");
        String password = credentials.get("password");
        return userService.addUser(login, password);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
