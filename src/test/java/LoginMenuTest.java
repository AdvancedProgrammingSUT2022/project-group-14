import controllers.TileController;
import controllers.UserController;
import enums.Commands;
import enums.tiles.TileBaseTypes;
import models.Tile;
import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import views.menus.LoginMenu;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
//import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginMenuTest {
    @Mock
    LoginMenu loginMenu;
    @Mock
    User user;


    @BeforeEach
    public void setUpUsers() throws IOException {
        UserController.readAllUsers();
    }

    @Test
    public void commandsStartWithTestFalse(){
        String testLoginEnum = "login mnu";
        Assertions.assertFalse(Commands.startsWith(testLoginEnum, Commands.LOGIN));
    }

    @Test
    public void commandsStartWithTestTrue(){
        String testCreateUser = "create user j2hebf";
        Assertions.assertTrue(Commands.startsWith(testCreateUser, Commands.CREATE_USER));
    }

    @Test
    public void commandsGetMatcherFalse(){
        String testExitMenu = "Exit menu";
        Assertions.assertNull(Commands.getMatcher(testExitMenu, Commands.EXIT_MENU));
    }

    @Test
    public void commandsGetMatcherTrue(){
        String testEnterMenu = "menu enter login menu";
        Matcher matcher = Commands.getMatcher(testEnterMenu, Commands.ENTER_MENU);
        Assertions.assertEquals("login menu", matcher.group("menuName"));
    }

    @Test
    public void checkCommandTest1(){
        String testCreateUser = "create user -n dandeBeDande -u hassan -p 12";
        when(loginMenu.checkCommand(testCreateUser)).thenCallRealMethod();
        boolean output = loginMenu.checkCommand(testCreateUser);
        verify(loginMenu).checkCreateUser(testCreateUser);
        Assertions.assertTrue(output);
    }

    @Test
    public void checkCommandTest2(){
        String testEnterMenu = "menu enter login menu";
        Matcher matcher = Commands.getMatcher(testEnterMenu, Commands.ENTER_MENU);
        when(loginMenu.checkCommand(testEnterMenu)).thenCallRealMethod();
        loginMenu.checkCommand(testEnterMenu);
        verify(loginMenu).checkEnterMenu(matcher);
    }

//    @Test
//    public void checkLoginTest1(){
//        String input = "login user -u ali -p 12";
//        //when(loginMenu.checkLogin(input)).thenCallRealMethod();
//        Matcher usernameMatcher = Commands.matcherFindsRegex(input, Commands.USERNAME);
//        Matcher passwordMatcher = Commands.matcherFindsRegex(input, Commands.PASSWORD);
//        when(loginMenu.usernameExists(usernameMatcher)).thenReturn(false);
//        when(UserController.getUserByUsername("ali")).thenReturn(user);
//        when(loginMenu.passwordIsCorrect(passwordMatcher, user)).thenReturn(true);
//        loginMenu.checkLogin(input);
//        verify(loginMenu).loginUser(user);
//
//    }
}
