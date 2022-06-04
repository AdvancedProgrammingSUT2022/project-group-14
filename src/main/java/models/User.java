package models;

import java.util.Objects;
import java.util.Random;

public class User {
    private String username;
    private String password;
    private String nickname;
    private int score;
    private String avatarFileAddress;

    public User(String username, String password, String nickname)
    {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.score = 0;
        this.avatarFileAddress = Objects.requireNonNull(getClass().getResource("/images/avatars/" + new Random().nextInt(1, 5) + ".jpg")).toExternalForm();
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

    public String getAvatarFileAddress() {
        return avatarFileAddress;
    }

    public void setAvatarFileAddress(String avatarFileAddress) {
        this.avatarFileAddress = avatarFileAddress;
    }

}
