package pl.kurs.loginbutton.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.kurs.loginbutton.dto.UserCredentialsDTO;
import pl.kurs.loginbutton.service.UserService;
import pl.kurs.loginbutton.user.UserDTO;


import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private UserDTO userDTO;

    @Test
    public void testLoginSuccess() throws Exception {
        // given
        UserCredentialsDTO credentials = new UserCredentialsDTO("user", "password");
        doReturn(true).when(userService).validateLogin(any(UserCredentialsDTO.class));

        //when/then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(credentials)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("okej!"));
    }

    @Test
    public void testLoginFailure() throws Exception {
        // given
        UserCredentialsDTO credentials = new UserCredentialsDTO("user", "wrongpassword");
        doReturn(false).when(userService).validateLogin(any(UserCredentialsDTO.class));

        //when/then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(credentials)))
                .andExpect(status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().string("źle!!!"));
    }

    @Test
    public void getAllUsers() throws Exception {
        //given
        doReturn(List.of(userDTO)).when(userService).getAllUsers();

        //when/then
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
}