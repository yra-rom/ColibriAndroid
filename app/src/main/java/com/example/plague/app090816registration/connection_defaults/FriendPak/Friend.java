package com.example.plague.app090816registration.connection_defaults.FriendPak;

public class Friend {
    private String nick;
    private String email;
    private String lastOnline;

    Friend(FriendBuilder builder){
        this.nick = builder.getNick();
        this.email = builder.getEmail();
        this.lastOnline = builder.getLastOnline();
    }

    public String getNick() {
        return nick;
    }

    public String getEmail() {
        return email;
    }

    public String getLastOnline() {
        return lastOnline;
    }
}
