package com.example.plague.app090816registration.Messaging.clients;

public class Message{
    private String message;
    private String time;
    private String from;
    private String to;

    public Message(String message, String time, String from, String to) {
        this.message = message;
        this.time = time;
        this.from = from;
        this.to = to;
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
