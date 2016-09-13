package com.example.plague.app090816registration.Tabs.ChatsList;

public class ChatItemBuilder {
    private String nick;
    private String email;
    private String time;
    private String last;

    public ChatItemBuilder nick(String nick){
        this.nick = nick;
        return this;
    }

    public ChatItemBuilder email(String email){
        this.email = email;
        return this;
    }

    public ChatItemBuilder time(String time){
        this.time = time;
        return this;
    }

    public ChatItemBuilder last(String last){
        this.last = last;
        return this;
    }

    public String getNick() {
        return nick;
    }

    public String getEmail() {
        return email;
    }

    public String getTime() {
        return time;
    }

    public String getLast() {
        return last;
    }

    public ChatItem build(){
        return new ChatItem(this);
    }
}
