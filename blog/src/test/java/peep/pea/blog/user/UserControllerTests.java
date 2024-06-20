package peep.pea.blog.user;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import peep.pea.blog.util.JwtUtil;

@WebMvcTest(UserController.class)
class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUtil jwtUtil;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService))
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testGetUserById() throws Exception {
        User user = new User(1L, "drew", "password", "example@email.com", "USER", LocalDateTime.parse("2024-01-01T00:00:00", formatter), "imissher.png");
        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/USERS/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("drew"));
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testCreateUser() throws Exception {
        User user = new User(1L, "drew", "password", "example@email.com", "USER", LocalDateTime.parse("2024-01-01T00:00:00", formatter), "imissher.png");
        when(userService.createUser(any(User.class))).thenReturn(user);

        String userJson = """
                {
                    "username": "drew",
                    "password": "password",
                    "email": "example@email.com",
                    "role": "USER",
                    "lastOnline": "2024-01-01T00:00:00",
                    "profilePictureUrl": "imissher.png"
                }
                """;

        mockMvc.perform(post("/USERS")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testGetAllUsers() throws Exception {
        List<User> users = Arrays.asList(
                new User(1L, "drew1", "password1", "example@email.com", "USER", LocalDateTime.parse("2024-01-01T00:00:00", formatter), "imissher.png"),
                new User(2L, "drew2", "password2", "example@email.com", "USER", LocalDateTime.parse("2024-01-01T00:00:00", formatter), "imissher.png")
        );
        Page<User> page = new PageImpl<>(users);
        Pageable pageable = PageRequest.of(0, 10);
        when(userService.getAllUsers(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/USERS")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("drew1"))
                .andExpect(jsonPath("$[1].username").value("drew2"));
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testUpdateUser() throws Exception {
        User userUpdate = new User(1L, "peep", "newpassword", "example@email.com", "USER", LocalDateTime.parse("2024-01-01T00:00:00", formatter), "imissher.png");
        when(userService.updateUser(eq(1L), any(User.class))).thenReturn(userUpdate);

        String userJson = """
                {
                    "username": "peep",
                    "password": "newpassword",
                    "email": "example@email.com",
                    "role": "USER",
                    "lastOnline": "2024-01-01T00:00:00"
                }
                """;

        mockMvc.perform(put("/USERS/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/USERS/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
