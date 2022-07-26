package Server.models.chats;

import java.util.ArrayList;
import java.util.Comparator;

public class Chat {
    private final ArrayList<String> usernames;
    private final ArrayList<Message> messages;
    private final String name;

    public Chat(ArrayList<String> usernames, String name) {
        this.usernames = usernames;
        this.name = name;
        this.messages = new ArrayList<>();
    }

    public ArrayList<String> getUsernames() {
        return usernames;
    }

    public void addUsername(String username) {
        this.usernames.add(username);
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
        this.messages.sort(Comparator.comparing(Message::getDate));
    }

    public String getName() {
        return name;
    }
}
