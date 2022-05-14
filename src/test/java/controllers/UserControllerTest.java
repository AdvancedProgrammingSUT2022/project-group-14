package controllers;

import enums.Commands;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.regex.Matcher;

import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();


    @BeforeEach
    public void setUpUsers() throws IOException {
        UserController.readAllUsers();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void saveAllUsersTest() throws IOException {
        UserController.saveAllUsers();
    }

    @Test
    public void addUserTest() {
        UserController.addUser("majid", "12", "majid");
        Assertions.assertNotNull(UserController.getUserByUsername("majid"));
    }

    @Test
    public void getUserByUsernameTest(){
        String username = "hossein";
        User user = UserController.getUserByUsername(username);
        Assertions.assertNull(user);
    }

    @Test
    public void getUserByNicknameTest(){
        String username = "hossein";
        User user = UserController.getUserByUsername(username);
        Assertions.assertNull(user);
    }

    @Test
    public void playerNumberIsCorrectTest(){
        String input = "play game -pl2 hassan";
        Matcher matcher = Commands.matcherFindsRegex(input, Commands.PLAYER_USERNAME);
        boolean isValid = UserController.playerNumberIsCorrect(matcher, 1);
        Assertions.assertFalse(isValid);
    }

    @Test
    public void usernameExistsInArraylistTest(){
        String username = "ali";
        ArrayList<String> usernames = new ArrayList<>();
        usernames.add("hassan");
        usernames.add("hossein");
        Assertions.assertFalse(UserController.usernameExistsInArraylist(username, usernames));
    }

    @Test
    public void checkPlayerValidationTest(){
        ArrayList<String> usernames = new ArrayList<>();
        usernames.add("ali");
        String input = "play game -pl1 hassan";
        Matcher matcher = Commands.matcherFindsRegex(input, Commands.PLAYER_USERNAME);
        UserController.checkPlayerValidation(matcher, usernames, 1);
        Assertions.assertEquals("", outputStreamCaptor.toString().trim());
    }

    @Test
    public void getUserByNickNameValidTest() {
        Assertions.assertNotNull(UserController.getUserByNickname("ali"));
    }

    @Test
    public void getUserByNickNameInValidTest() {
        Assertions.assertNull(UserController.getUserByNickname("majid"));
    }

    @AfterEach
    public void closing(){
        System.setOut(standardOut);
    }
}
