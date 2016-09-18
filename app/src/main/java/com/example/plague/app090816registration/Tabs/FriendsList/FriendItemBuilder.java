package com.example.plague.app090816registration.Tabs.FriendsList;

import com.example.plague.app090816registration.connection_defaults.FriendPak.FriendBuilder;

public class FriendItemBuilder {
    private String name;
    private String email;
    private boolean isOnline;

    public FriendItemBuilder name(String name){
        this.name = name;
        return this;
    }

    public FriendItemBuilder online(boolean isOnline){
        this.isOnline = isOnline;
        return this;
    }

    public FriendItemBuilder email(String email){
        this.email = email;
        return this;
    }

    public String getName() {
        return name;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public String getEmail() {
        return email;
    }

    public FriendItem build(){
        return new FriendItem(this);
    }
}
