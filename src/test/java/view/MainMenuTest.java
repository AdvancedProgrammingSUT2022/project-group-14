package view;

import controllers.UserController;
import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import views.menus.LoginMenu;
import views.menus.MainMenu;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import static org.mockito.Mockito.when;

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

}
