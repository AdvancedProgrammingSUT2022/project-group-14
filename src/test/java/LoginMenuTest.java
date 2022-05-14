import controllers.TileController;
import controllers.UserController;
import enums.Commands;
import enums.tiles.TileBaseTypes;
import models.Tile;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import views.menus.LoginMenu;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
//import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginMenuTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock
    LoginMenu loginMenu;
    @Mock
    User user;



    @BeforeEach
    public void setUpUsers() throws IOException {
        UserController.readAllUsers();
        System.setOut(new PrintStream(outputStreamCaptor));
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
        boolean output = loginMenu.checkCommand(testEnterMenu);
        verify(loginMenu).checkEnterMenu(any(Matcher.class));
        Assertions.assertTrue(output);
    }

    @Test
    public void checkLoginTest1AllIsFine(){
        String input = "login user -u ali -p 12";
        doCallRealMethod().when(loginMenu).checkLogin(input);
        when(loginMenu.usernameExists(any(Matcher.class))).thenReturn(true);
        when(loginMenu.passwordIsCorrect(any(Matcher.class), any(User.class))).thenReturn(true);
        loginMenu.checkLogin(input);
        verify(loginMenu).loginUser(any(User.class));
        Assertions.assertEquals("user logged in successfully!", outputStreamCaptor.toString().trim());

    }

    @Test
    public void checkLoginTest2PasswordIsIncorrect(){
        String input = "login user -p 123 -u ali";
        doCallRealMethod().when(loginMenu).checkLogin(input);
        when(loginMenu.usernameExists(any(Matcher.class))).thenReturn(true);
        when(loginMenu.passwordIsCorrect(any(Matcher.class), any(User.class))).thenReturn(false);
        loginMenu.checkLogin(input);
        Assertions.assertEquals("Username and password didn’t match!", outputStreamCaptor.toString().trim());
    }

    @Test
    public void checkEnterMenuLoginMenu(){
        String input = "menu enter login menu";
        Matcher matcher = Commands.getMatcher(input, Commands.ENTER_MENU);
        doCallRealMethod().when(loginMenu).checkEnterMenu(matcher);
        loginMenu.checkEnterMenu(matcher);
        Assertions.assertEquals("this is your current menu", outputStreamCaptor.toString().trim());
    }

    @Test
    public void checkEnterMenuProfileMenu(){
        String input = "menu enter profile menu";
        Matcher matcher = Commands.getMatcher(input, Commands.ENTER_MENU);
        doCallRealMethod().when(loginMenu).checkEnterMenu(matcher);
        loginMenu.checkEnterMenu(matcher);
        Assertions.assertEquals("menu navigation is not possible", outputStreamCaptor.toString().trim());
    }

    @Test
    public void checkEnterMenuGameMenu(){
        String input = "menu enter game menu";
        Matcher matcher = Commands.getMatcher(input, Commands.ENTER_MENU);
        doCallRealMethod().when(loginMenu).checkEnterMenu(matcher);
        loginMenu.checkEnterMenu(matcher);
        Assertions.assertEquals("menu navigation is not possible", outputStreamCaptor.toString().trim());
    }

    @Test
    public void checkEnterMenuMainMenu(){
        String input = "menu enter main menu";
        Matcher matcher = Commands.getMatcher(input, Commands.ENTER_MENU);
        doCallRealMethod().when(loginMenu).checkEnterMenu(matcher);
        loginMenu.checkEnterMenu(matcher);
        Assertions.assertEquals("please login first", outputStreamCaptor.toString().trim());
    }

    @Test
    public void checkCreateUserUsernameIsRepetitive(){
        String input = "create user -n dande -u hassan -p 12";
        doCallRealMethod().when(loginMenu).checkCreateUser(input);
        when(loginMenu.usernameExists(any(Matcher.class))).thenCallRealMethod();
        loginMenu.checkCreateUser(input);
        Assertions.assertEquals("user with username hassan already exists", outputStreamCaptor.toString().trim());
    }

    @Test
    public void checkCreateUserNicknameIsRepetitive(){
        String input = "create user -n dande -u hassan -p 12";
        doCallRealMethod().when(loginMenu).checkCreateUser(input);
        when(loginMenu.usernameExists(any(Matcher.class))).thenReturn(false);
        when(loginMenu.nicknameExists(any(Matcher.class))).thenCallRealMethod();
        loginMenu.checkCreateUser(input);
        Assertions.assertEquals("user with nickname dande already exists", outputStreamCaptor.toString().trim());
    }

    @Test
    public void checkCreateUserAllIsFine(){
        String input = "create user -n alipour -u hamid -p 12";
        doCallRealMethod().when(loginMenu).checkCreateUser(input);
        when(loginMenu.usernameExists(any(Matcher.class))).thenCallRealMethod();
        when(loginMenu.nicknameExists(any(Matcher.class))).thenCallRealMethod();
        loginMenu.checkCreateUser(input);
        Assertions.assertEquals("user created successfully!", outputStreamCaptor.toString().trim());
    }

    @AfterEach
    public void closing(){
        System.setOut(standardOut);
    }
}
