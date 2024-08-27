package pl.kurs.loginbutton;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Wyłącz CSRF dla testów
                .authorizeRequests()
                .requestMatchers("/api/users/register", "/api/users/login").permitAll() // Publiczny dostęp do rejestracji i logowania
                .anyRequest().authenticated(); // Reszta wymaga uwierzytelnienia
        return http.build();
    }
}

