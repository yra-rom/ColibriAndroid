package com.example.plague.app090816registration.Tabs.FriendsList;

public class FriendItem {
    private String nick;
    private boolean isOnline;
    private String email;

    public FriendItem(FriendItemBuilder builder) {
        this.nick = builder.getName();
        this.isOnline = builder.isOnline();
        this.email = builder.getEmail();
    }

    public String getEmail() {
        return email;
    }

    public String getNick() {
        return nick;
    }

    public boolean isOnline() {
        return isOnline;
    }
}
