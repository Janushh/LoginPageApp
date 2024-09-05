package pl.kurs.loginbutton.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.kurs.loginbutton.dto.UserCredentialsDTO;
import pl.kurs.loginbutton.service.UserService;
import pl.kurs.loginbutton.user.UserDTO;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
//@WebMvcTest(UserController.class)
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private UserDTO userDTO;


    @Test
    public void testApiResponseSuccessLoginPut() throws Exception {
        //given
        UserCredentialsDTO credentials = new UserCredentialsDTO("user", "password");
        given(userService.validateLogin(credentials)).willReturn(true);

        //when/then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"login\": \"user\", \"password\": \"password\"}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("okej!"));
    }

    @Test
    public void testApiResponseFailureLoginPut2() throws Exception {
        //given
        UserCredentialsDTO credentials = new UserCredentialsDTO("user", "wrongpassword");
        given(userService.validateLogin(credentials)).willReturn(false);

        //when/then
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"login\": \"user\", \"password\": \"wrongpassword\"}"))
                        .andExpect(status().isUnauthorized())
                        .andExpect(MockMvcResultMatchers.content().string("źle!!!"));
    }

    @Test
    public void testApiResponsePost() throws Exception {
        UserCredentialsDTO credentials = new UserCredentialsDTO("newuser", "password123");
        UserDTO userDTO = new UserDTO(1, "newuser", "password123");

        when(userService.addUser(any(UserCredentialsDTO.class))).thenReturn(userDTO);

        //when/then
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(credentials))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.login").value("newuser"))
                .andExpect(jsonPath("$.password").value("password123"));
    }

    @Test
    public void testApiResponseGet() throws Exception {
        //given
        UserDTO userDTO = new UserDTO(1, "user1", "password1");
        UserDTO userDTO2 = new UserDTO(2, "user2", "password2");
        List<UserDTO> userList = List.of(userDTO, userDTO2);
        when(userService.getAllUsers()).thenReturn(userList);

        //when/then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)

        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].login").value("user1"))
                .andExpect(jsonPath("$[0].password").value("password1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].login").value("user2"))
                .andExpect(jsonPath("$[1].password").value("password2"));


//                .andExpect(content().json("{\"id\":1," +
//                        "\"login\":\"user1\"," +
//                        "\"password\":\"password1\"}"))
//                .andExpectAll(content().json("{\"id\":2," +
//                        "\"login\":\"user2\"," +
//                        "\"password\":\"password2\"}"));

    }
}