package com.example.plague.app090816registration.Tabs.ChatsList;

public class ChatItem {
    private String nick;
    private String email;
    private String time;
    private String last;

    ChatItem(ChatItemBuilder builder){
        this.nick = builder.getNick();
        this.email = builder.getEmail();
        this.time = builder.getTime();
        this.last = builder.getLast();
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

    public String getEmail() {
        return email;
    }
}
