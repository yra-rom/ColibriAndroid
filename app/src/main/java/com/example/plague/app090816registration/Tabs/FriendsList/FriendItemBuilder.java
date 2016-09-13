package com.example.plague.app090816registration.Tabs.FriendsList;

public class FriendItemBuilder {
    private String name;
    private boolean isOnline;

    public FriendItemBuilder name(String name){
        this.name = name;
        return this;
    }

    public FriendItemBuilder online(boolean isOnline){
        this.isOnline = isOnline;
        return this;
    }

    public String getName() {
        return name;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public FriendItem build(){
        return new FriendItem(this);
    }
}
