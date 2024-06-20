package peep.pea.blog.user;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

@JsonTest
class UsersJsonTest {

    @Autowired
    private JacksonTester<User> json;

    @Autowired
    private JacksonTester<User[]> jsonList;

    private User[] users;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @BeforeEach
    void setUp() {
        users = Arrays.array(
            new User(1001L, "drew", "password1", "drew@example.com", "USER", LocalDateTime.parse("2022-06-18T12:00:00", formatter), "url1"),
            new User(1002L, "jane", "password2", "jane@example.com", "USER", LocalDateTime.parse("2022-06-19T15:00:00", formatter), "url2"),
            new User(1003L, "john", "password3", "john@example.com", "ADMIN", LocalDateTime.parse("2022-06-20T18:00:00", formatter), "url3"),
            new User(1004L, "alice", "password4", "alice@example.com", "USER", LocalDateTime.parse("2022-06-21T21:00:00", formatter), "url4"),
            new User(1005L, "bob", "password5", "bob@example.com", "USER", LocalDateTime.parse("2022-06-22T08:00:00", formatter), "url5")
        );
    }

    @Test
    void userSerializationTest() throws IOException {
        User user = users[0];
        assertThat(json.write(user)).isStrictlyEqualToJson("single.json");
        assertThat(json.write(user)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(user)).extractingJsonPathNumberValue("@.id")
                .isEqualTo(1001);
        assertThat(json.write(user)).hasJsonPathStringValue("@.username");
        assertThat(json.write(user)).extractingJsonPathStringValue("@.username")
                .isEqualTo("drew");
        assertThat(json.write(user)).hasJsonPathStringValue("@.password");
        assertThat(json.write(user)).extractingJsonPathStringValue("@.password")
                .isEqualTo("password1");
        assertThat(json.write(user)).hasJsonPathStringValue("@.role");
        assertThat(json.write(user)).extractingJsonPathStringValue("@.role")
                .isEqualTo("USER");
        assertThat(json.write(user)).hasJsonPathStringValue("@.email");
        assertThat(json.write(user)).extractingJsonPathStringValue("@.email")
                .isEqualTo("drew@example.com");
        assertThat(json.write(user)).hasJsonPathStringValue("@.profilePictureUrl");
        assertThat(json.write(user)).extractingJsonPathStringValue("@.profilePictureUrl")
                .isEqualTo("url1");
    }

    @Test
    void userDeserializationTest() throws IOException {
        String expected = """
                {
                    "id": 1001,
                    "username": "drew",
                    "password": "password1",
                    "email": "drew@example.com",
                    "role": "USER",
                    "lastOnline": "2022-06-18T12:00:00",
                    "profilePictureUrl": "url1"
                }
                """;
        User expectedUser = new User(1001L, "drew", "password1", "drew@example.com", "USER", LocalDateTime.parse("2022-06-18T12:00:00", formatter), "url1");
        assertThat(json.parse(expected)).isEqualTo(expectedUser);
    }

    @Test
    void userListSerializationTest() throws IOException {
        assertThat(jsonList.write(users)).isStrictlyEqualToJson("list.json");
    }

    @Test
    void userListDeserializationTest() throws IOException {
        String expected = """
            [
                {
                    "id": 1001,
                    "username": "drew",
                    "password": "password1",
                    "email": "drew@example.com",
                    "role": "USER",
                    "lastOnline": "2022-06-18T12:00:00",
                    "profilePictureUrl": "url1"
                },
                {
                    "id": 1002,
                    "username": "jane",
                    "password": "password2",
                    "email": "jane@example.com",
                    "role": "USER",
                    "lastOnline": "2022-06-19T15:00:00",
                    "profilePictureUrl": "url2"
                },
                {
                    "id": 1003,
                    "username": "john",
                    "password": "password3",
                    "email": "john@example.com",
                    "role": "ADMIN",
                    "lastOnline": "2022-06-20T18:00:00",
                    "profilePictureUrl": "url3"
                },
                {
                    "id": 1004,
                    "username": "alice",
                    "password": "password4",
                    "email": "alice@example.com",
                    "role": "USER",
                    "lastOnline": "2022-06-21T21:00:00",
                    "profilePictureUrl": "url4"
                },
                {
                    "id": 1005,
                    "username": "bob",
                    "password": "password5",
                    "email": "bob@example.com",
                    "role": "USER",
                    "lastOnline": "2022-06-22T08:00:00",
                    "profilePictureUrl": "url5"
                }
            ]
            """;
        assertThat(jsonList.parse(expected)).isEqualTo(users);
    }    
}
