package Client.models;


import Server.controllers.UserController;
import Server.enums.Avatars;
import Server.models.chats.Chat;
import javafx.scene.image.Image;

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

    public User(String username, String password, String nickname)
    {
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
        for (User user : UserController.getUsers()) {
            usernames.add(user.getUsername());
        }
        chats.put("Public Chat", new Chat(usernames, "Public Chat"));
        for (User user : UserController.getUsers()) {
            user.getChats().get("Public Chat").addUsername(this.username);
        }
        invitations = new ArrayList<>();
        peopleInLobby = new ArrayList<>(List.of(username));
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getScore() {
        return this.score;
    }

    public void changeScore(int score) {
        this.score += score;
    }

    public Date getDateOfLastWin() {
        return dateOfLastWin;
    }

    public void setDateOfLastWin(Date dateOfLastWin) {
        this.dateOfLastWin = dateOfLastWin;
    }

    public Image getImage() {
        if (Avatars.contains(this.avatarFileAddress)) {
            return new Image(Avatars.valueOf(avatarFileAddress).getAddress());
        } else {
            return new Image(avatarFileAddress);
        }
    }

    public void setAvatarFileAddress(String avatarFileAddress) {
        this.avatarFileAddress = avatarFileAddress;
    }

    public Date getDateOfLastLogin() {
        return dateOfLastLogin;
    }

    public void setDateOfLastLogin(Date dateOfLastLogin) {
        this.dateOfLastLogin = dateOfLastLogin;
    }

    public HashMap<String, Chat> getChats() {
        return chats;
    }

    public void addChats(Chat chat) {
        this.chats.put(chat.getName(), chat);
    }

    public ArrayList<String> getInvitations() {
        return invitations;
    }

    public void addInvitations(String name) {
        invitations.remove("Invitation from " + name);
        invitations.add("Invitation from " + name);
    }

    public void removeInvitation(String invitation) {
        invitations.remove(invitation);
    }

    public ArrayList<String> getPeopleInLobby() {
        return peopleInLobby;
    }

    public void addPersonToLobby(String username) {
        peopleInLobby.add(username);
    }

    public void removePersonFromLobby(String username) {
        peopleInLobby.remove(username);
    }

    public void resetPeopleInLobby() {
        for (String s : peopleInLobby) {
            if (!s.equals(username))
                Objects.requireNonNull(UserController.getUserByUsername(s)).removePersonFromLobby(username);
        }
        peopleInLobby.clear();
        peopleInLobby.add(username);
    }
}
