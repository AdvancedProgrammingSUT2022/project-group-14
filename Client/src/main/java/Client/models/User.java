package Client.models;


import Client.enums.Avatars;
import Client.models.chats.Chat;
import javafx.scene.image.Image;

import java.io.BufferedWriter;
import java.util.*;

public class User {
    private String username;
    private String password;
    private String nickname;
    private int score;
    private Date dateOfLastWin;
    private Date dateOfLastLogin;
    private String avatarFileAddress;
    private final HashMap<String, Chat> chats;
    private final ArrayList<String> invitations;
    private final ArrayList<String> peopleInLobby;
    private String token;
    transient private BufferedWriter bufferedWriter;

    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.score = 0;
        this.dateOfLastWin = null;
        this.dateOfLastLogin = null;
        this.avatarFileAddress = Avatars.getAllAddresses().get(new Random().nextInt(0, 52));
        chats = new HashMap<>();
        ArrayList<String> usernames = new ArrayList<>();
        usernames.add(this.username);
        invitations = new ArrayList<>();
        peopleInLobby = new ArrayList<>(List.of(username));
    }

    public String getUsername() {
        return this.username;
    }

    public int getScore() {
        return this.score;
    }

    public Date getDateOfLastWin() {
        return dateOfLastWin;
    }

    public Image getImage() {
        if (Avatars.contains(this.avatarFileAddress)) {
            return new Image(Avatars.valueOf(avatarFileAddress).getAddress());
        } else {
            return new Image(avatarFileAddress);
        }
    }

    public Date getDateOfLastLogin() {
        return dateOfLastLogin;
    }

    public HashMap<String, Chat> getChats() {
        return chats;
    }

    public ArrayList<String> getInvitations() {
        return invitations;
    }

    public ArrayList<String> getPeopleInLobby() {
        return peopleInLobby;
    }

    public String getToken() {
        return token;
    }
}
