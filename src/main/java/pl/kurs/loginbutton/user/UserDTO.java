package pl.kurs.loginbutton.user;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    private int  id;
    private String login;
    private String password; 
}
