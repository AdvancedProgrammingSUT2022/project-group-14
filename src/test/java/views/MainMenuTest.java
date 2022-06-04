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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Matcher;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MainMenuTest {
    @Mock
    MainMenu mainMenu;
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
    public void runTest(){
        MainMenu newMainMenu = new MainMenu(scanner, user);
        when(scanner.nextLine()).thenReturn("menu exit");
        newMainMenu.run();
    }

    @Test
    public void checkCommandTest1(){
        String input = "salam";
        when(mainMenu.checkCommand(input)).thenCallRealMethod();
        boolean output = mainMenu.checkCommand(input);
        Assertions.assertEquals("invalid command", outputStreamCaptor.toString().trim());
        Assertions.assertTrue(output);
    }

    @Test
    public void checkCommandTest2(){
        String input = "menu enter login menu";
        when(mainMenu.checkCommand(input)).thenCallRealMethod();
        when(mainMenu.checkEnterMenu(any(Matcher.class))).thenReturn(true);
        boolean output = mainMenu.checkCommand(input);
        Assertions.assertFalse(output);
    }

    @Test
    public void checkEnterMenuLoginMenu(){
        String input = "menu enter login menu";
        Matcher matcher = Commands.getMatcher(input, Commands.ENTER_MENU);
        doCallRealMethod().when(mainMenu).checkEnterMenu(matcher);
        boolean output = mainMenu.checkEnterMenu(matcher);
        Assertions.assertEquals("user logged out and returned to login menu successfully", outputStreamCaptor.toString().trim());
        Assertions.assertTrue(output);
    }

    @Test
    public void checkEnterMenuProfileMenu(){
        String input = "menu enter profile menu";
        Matcher matcher = Commands.getMatcher(input, Commands.ENTER_MENU);
        MainMenu newMainMenu = new MainMenu(scanner, user);
//        doCallRealMethod().when(mainMenu).checkEnterMenu(matcher);
//        doCallRealMethod().when(mainMenu).goToProfileMenu();
        when(scanner.nextLine()).thenReturn("menu exit");
        boolean output = newMainMenu.checkEnterMenu(matcher);
        Assertions.assertEquals("user successfully entered profile menu\nuser successfully transferred to main menu", outputStreamCaptor.toString().trim());
        Assertions.assertFalse(output);


    }

    @Test
    public void checkEnterMenuGameMenu(){
        String input = "menu enter game menu";
        Matcher matcher = Commands.getMatcher(input, Commands.ENTER_MENU);
        doCallRealMethod().when(mainMenu).checkEnterMenu(matcher);
        boolean output = mainMenu.checkEnterMenu(matcher);
        Assertions.assertEquals("menu navigation is not possible", outputStreamCaptor.toString().trim());
        Assertions.assertFalse(output);
    }

    @Test
    public void checkEnterMenuMainMenu(){
        String input = "menu enter main menu";
        Matcher matcher = Commands.getMatcher(input, Commands.ENTER_MENU);
        doCallRealMethod().when(mainMenu).checkEnterMenu(matcher);
        boolean output = mainMenu.checkEnterMenu(matcher);
        Assertions.assertEquals("this is your current menu", outputStreamCaptor.toString().trim());
    }

    @Test
    public void checkPlayGameTestAllIsFine(){
        MainMenu newMainMenu = new MainMenu(scanner, user);
        String input = "play game -pl1 hassan";
        when(user.getUsername()).thenReturn("ali");
        when(scanner.nextLine()).thenReturn("end game");
        newMainMenu.checkPlayGame(input);

    }

    @Test
    public void checkPlayGameTestRepetitivePlayer(){
        MainMenu newMainMenu = new MainMenu(scanner, user);
        String input = "play game -pl1 ali";
        when(user.getUsername()).thenReturn("ali");
        newMainMenu.checkPlayGame(input);
        Assertions.assertEquals("one of the usernames is repetitive", outputStreamCaptor.toString().trim());

    }

    @AfterEach
    public void closing() throws IOException {
        System.setOut(standardOut);
    }

}
