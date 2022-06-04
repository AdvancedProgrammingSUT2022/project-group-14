package models;

import java.util.Date;

public class User {
    private String username;
    private String password;
    private String nickname;
    private int score;
    private Date dateOfLastWin = null;

    public User(String username, String password, String nickname)
    {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.score = 0;
        this.dateOfLastWin = new Date();
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
}
