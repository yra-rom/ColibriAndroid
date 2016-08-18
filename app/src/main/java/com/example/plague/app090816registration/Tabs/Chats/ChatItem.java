package com.example.plague.app090816registration.Tabs.Chats;

public class ChatItem {
    private String nick;
    private String time;
    private String last;

    public ChatItem(String nick, String last, String time) {
        this.nick = nick;
        this.time = time;
        this.last = last;
    }

    public String getNick() {
        return nick;
    }

    public String getTime() {
        return time;
    }

    public String getLast() {
        return last;
    }
}
