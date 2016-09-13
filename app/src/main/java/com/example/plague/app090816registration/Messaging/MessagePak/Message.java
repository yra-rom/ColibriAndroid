package com.example.plague.app090816registration.Messaging.MessagePak;

public class Message{
    private String message;
    private String time;
    private String from;
    private String to;

    Message(MessageBuilder builder){
        this.message = builder.getMessage();
        this.time = builder.getTime();
        this.from = builder.getFrom();
        this.to = builder.getTo();
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

}
