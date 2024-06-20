package peep.pea.blog.user;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class) 
class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;


    @Test
    void testCreateUser() {
        User user = new User(null, "drew", "password", "example@email.com", "USER", LocalDateTime.parse("2024-01-01T00:00:00", formatter), "imissher.png");
        User savedUser = new User(1L, "drew", "password", "example@email.com", "USER", LocalDateTime.parse("2024-01-01T00:00:00", formatter), "imissher.png");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.createUser(user);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getUsername()).isEqualTo("drew");
    }

    @Test
    void testGetUserById() {
        User user = new User(1L, "drew", "password", "example@email.com", "USER", LocalDateTime.parse("2024-01-01T00:00:00", formatter), "imissher.png");
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));

        User foundUser = userService.getUserById(1L).orElse(null);

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getId()).isEqualTo(1L);
        assertThat(foundUser.getUsername()).isEqualTo("drew");
    }
}