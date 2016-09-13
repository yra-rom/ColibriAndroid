package com.example.plague.app090816registration.connection_defaults.FriendPak;

public class FriendBuilder {
    private String nick;
    private String email;
    private String lastOnline;

    public FriendBuilder nick(String nick){
        this.nick = nick;
        return this;
    }

    public FriendBuilder email(String email){
        this.email = email;
        return this;
    }

    public FriendBuilder lastOnline(String lastOnline){
        this.lastOnline = lastOnline;
        return this;
    }

    public Friend build(){
        return new Friend(this);
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
