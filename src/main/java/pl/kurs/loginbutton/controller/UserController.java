package pl.kurs.loginbutton.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.loginbutton.dto.UserCredentialsDTO;
import pl.kurs.loginbutton.service.UserService;
import pl.kurs.loginbutton.user.UserDTO;

import java.util.List;



@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserCredentialsDTO credentials) {
        if (userService.validateLogin(credentials)) {
            return ResponseEntity.ok("okej!");
        } else {
            return ResponseEntity.status(401).body("źle!!!");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserCredentialsDTO credentials) {
        UserDTO userDTO = userService.addUser(credentials);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}