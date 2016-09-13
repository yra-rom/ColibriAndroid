package com.example.plague.app090816registration.Messaging.MessagePak;

public class MessageBuilder {

    private String message;
    private String time;
    private String from;
    private String to;

    public MessageBuilder message(String message){
        this.message=message;
        return this;
    }

    public MessageBuilder time(String time){
        this.time=time;
        return this;
    }

    public MessageBuilder from(String from){
        this.from=from;
        return this;
    }

    public MessageBuilder to(String to){
        this.to=to;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Message build(){
        return new Message(this);
    }

}