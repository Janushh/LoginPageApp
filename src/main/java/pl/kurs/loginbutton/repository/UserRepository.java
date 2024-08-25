package pl.kurs.loginbutton.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.loginbutton.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
}
