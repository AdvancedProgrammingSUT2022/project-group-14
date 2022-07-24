package Client.models.chats;

import java.util.Date;

public class Message {
    private String text;
    private String senderUsername;
    private Date date;
    private boolean isSeen;

    public Message(String text, String senderUsername, Date date) {
        this.text = text;
        this.senderUsername = senderUsername;
        this.date = date;
        this.isSeen = false;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }
}
