package views;

import controllers.UserController;
import enums.Commands;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import views.menus.MainMenu;
import views.menus.ProfileMenu;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Matcher;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProfileMenuTest {
    @Mock
    ProfileMenu profileMenu;
    @Mock
    Scanner scanner;
    @Mock
    User user;

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpUsers() throws IOException {
        UserController.readAllUsers();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void checkCommandTest(){
        String input = "menu enter main menu";
        when(profileMenu.checkCommand(input)).thenCallRealMethod();
        when(profileMenu.checkEnterMenu(any(Matcher.class))).thenCallRealMethod();
        boolean output = profileMenu.checkCommand(input);
        Assertions.assertFalse(output);
        Assertions.assertEquals("user successfully transferred to main menu", outputStreamCaptor.toString().trim());

    }

    @Test
    public void checkEnterMenuLoginMenu(){
        String input = "menu enter login menu";
        Matcher matcher = Commands.getMatcher(input, Commands.ENTER_MENU);
        doCallRealMethod().when(profileMenu).checkEnterMenu(matcher);
        profileMenu.checkEnterMenu(matcher);
        Assertions.assertEquals("menu navigation is not possible", outputStreamCaptor.toString().trim());
    }

    @Test
    public void checkEnterMenuProfileMenu(){
        String input = "menu enter profile menu";
        Matcher matcher = Commands.getMatcher(input, Commands.ENTER_MENU);
        doCallRealMethod().when(profileMenu).checkEnterMenu(matcher);
        profileMenu.checkEnterMenu(matcher);
        Assertions.assertEquals("this is your current menu", outputStreamCaptor.toString().trim());
    }

    @Test
    public void checkEnterMenuGameMenu(){
        String input = "menu enter game menu";
        Matcher matcher = Commands.getMatcher(input, Commands.ENTER_MENU);
        doCallRealMethod().when(profileMenu).checkEnterMenu(matcher);
        profileMenu.checkEnterMenu(matcher);
        Assertions.assertEquals("menu navigation is not possible", outputStreamCaptor.toString().trim());
    }

    @Test
    public void checkChangeProfileTestChangeNickname(){
        String input = "change profile -n haji";
        ProfileMenu newProfileMenu = new ProfileMenu(scanner, user);
        newProfileMenu.checkChangeProfile(input);
    }

    @Test
    public  void checkChangeProfileTestChangeUsernameTest1(){
        String input = "change profile -p -c 12 -ne 123";
        ProfileMenu newProfileMenu = new ProfileMenu(scanner, user);
        when(user.getPassword()).thenReturn("12");
        newProfileMenu.checkChangeProfile(input);
    }

    @Test
    public  void checkChangeProfileTestChangeUsernameTest2(){
        String input = "change profile -p -c 12 -ne 12";
        ProfileMenu newProfileMenu = new ProfileMenu(scanner, user);
        when(user.getPassword()).thenReturn("12");
        newProfileMenu.checkChangeProfile(input);
    }

    @Test
    public  void checkChangeProfileTestChangeUsernameTest3(){
        String input = "change profile -p -c 1233 -ne 123";
        ProfileMenu newProfileMenu = new ProfileMenu(scanner, user);
        when(user.getPassword()).thenReturn("12");
        newProfileMenu.checkChangeProfile(input);
    }


    @AfterEach
    public void closing() throws IOException {
        System.setOut(standardOut);
    }
}
