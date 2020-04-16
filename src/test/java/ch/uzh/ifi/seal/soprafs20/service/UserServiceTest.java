package ch.uzh.ifi.seal.soprafs20.service;

import ch.uzh.ifi.seal.soprafs20.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs20.entity.User;
import ch.uzh.ifi.seal.soprafs20.exceptions.API.POST.PostRequestException409;
import ch.uzh.ifi.seal.soprafs20.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

        // given
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("testPassword");

        // when -> any object is being save in the userRepository -> return the dummy testUser
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(testUser);
    }

    @Test
    public void createUser_validInputs_success() {
        // when -> any object is being save in the userRepository -> return the dummy testUser
        User createdUser = userService.createUser(testUser);

        // then
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());

        assertEquals(testUser.getId(), createdUser.getId());
        assertEquals(testUser.getName(), createdUser.getName());
        assertEquals(testUser.getUsername(), createdUser.getUsername());
        assertNotNull(createdUser.getToken());
        assertEquals(UserStatus.OFFLINE, createdUser.getStatus());
        assertEquals(createdUser.getGamesPlayed(), 0);
        assertEquals(createdUser.getScore(), 0);
        assertEquals(createdUser.getInGame(), false);
    }

    @Test
    public void checkIfNameHasWhitespaces_throwsException() {
        User emptyUser = new User();
        emptyUser.setUsername("Test");
        String exceptionMessage = "The field 'NAME' must not contain any whitespaces! Therefore, the user could not be created!";

        //name = whitespace
        emptyUser.setName(" ");
        PostRequestException409 exception = assertThrows(PostRequestException409.class, () -> userService.createUser(emptyUser), exceptionMessage);
        assertEquals(exceptionMessage, exception.getMessage());

        //whitespace before name
        emptyUser.setName(" Tom");
        exception = assertThrows(PostRequestException409.class, () -> userService.createUser(emptyUser), exceptionMessage);
        assertEquals(exceptionMessage, exception.getMessage());

        //whitespace after name
        emptyUser.setName("Anna ");
        exception = assertThrows(PostRequestException409.class, () -> userService.createUser(emptyUser), exceptionMessage);
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    public void checkIfUsernameHasWhitespaces_throwsException() {
        User emptyUser = new User();
        emptyUser.setName("Test");
        String exceptionMessage = "The field 'USERNAME' must not contain any whitespaces! Therefore, the user could not be created!";

        //username = whitespace
        emptyUser.setUsername(" ");
        PostRequestException409 exception = assertThrows(PostRequestException409.class, () -> userService.createUser(emptyUser), exceptionMessage);
        assertEquals(exceptionMessage, exception.getMessage());

        //whitespace before username
        emptyUser.setUsername(" Tom");
        exception = assertThrows(PostRequestException409.class, () -> userService.createUser(emptyUser), exceptionMessage);
        assertEquals(exceptionMessage, exception.getMessage());

        //whitespace after username
        emptyUser.setUsername("Anna ");
        exception = assertThrows(PostRequestException409.class, () -> userService.createUser(emptyUser), exceptionMessage);
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    public void checkIfPasswordHasWhitespaces_throwsException() {
        User emptyUser = new User();
        emptyUser.setName("Test");
        emptyUser.setUsername("Test");
        String exceptionMessage = "The field 'PASSWORD' must not contain any whitespaces! Therefore, the user could not be created!";

        //password = whitespace
        emptyUser.setPassword(" ");
        PostRequestException409 exception = assertThrows(PostRequestException409.class, () -> userService.createUser(emptyUser), exceptionMessage);
        assertEquals(exceptionMessage, exception.getMessage());

        //whitespace before password
        emptyUser.setPassword(" 123");
        exception = assertThrows(PostRequestException409.class, () -> userService.createUser(emptyUser), exceptionMessage);
        assertEquals(exceptionMessage, exception.getMessage());

        //whitespace after password
        emptyUser.setPassword("123 ");
        exception = assertThrows(PostRequestException409.class, () -> userService.createUser(emptyUser), exceptionMessage);
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    public void updateUser_validInputs_success() {

        User testUpdateUser = new User();
        testUpdateUser.setId(1L);
        testUpdateUser.setName("testName");
        testUpdateUser.setUsername("testUsername");
        testUpdateUser.setPassword("testPassword");
        Optional<User> userOptional = Optional.of(testUpdateUser);

        // when -> any object is being update in the userRepository -> return the dummy testUpdateUser
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(testUpdateUser);
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(userOptional);


        // when -> any object is being save in the userRepository -> return the dummy testUser
        User updatedUser = userService.updateUser(testUpdateUser.getId(), testUpdateUser);

        // then
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());

        assertEquals(testUpdateUser.getId(), updatedUser.getId());
        assertEquals(testUpdateUser.getName(), updatedUser.getName());
        assertEquals(testUpdateUser.getUsername(), updatedUser.getUsername());

    }

}
