package peep.pea.blog.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.test.context.ContextConfiguration;@DataJdbcTest
@ContextConfiguration(classes = UserRepositoryTests.Config.class)
class UserRepositoryTests {

    @Configuration
    @EnableJdbcRepositories(basePackages = "peep.pea.blog.user")
    @ComponentScan(basePackages = "peep.pea.blog.user")
    @Import({UserService.class})
    static class Config {
    }

    @Autowired
    private UserRepository userRepository;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Test
    void testFindByUsername() {
        User user = new User(1L, "drew", "password", "example@email.com", "USER", LocalDateTime.parse("2024-01-01T00:00:00", formatter), "imissher.png");
        userRepository.save(user);
        Optional<User> foundUser = userRepository.findByUsername("drew");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get()).isEqualTo(user);
    }
}

