package com.example.plague.app090816registration.Tabs.FriendsList;

public class FriendItem {
    private String name;
    private boolean isOnline;

    public FriendItem(FriendItemBuilder builder) {
        this.name = builder.getName();
        this.isOnline = builder.isOnline();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }
}
