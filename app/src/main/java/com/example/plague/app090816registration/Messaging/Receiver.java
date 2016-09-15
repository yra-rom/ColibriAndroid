package com.example.plague.app090816registration.Messaging;

import com.example.plague.app090816registration.Messaging.MessagePak.Message;
import com.example.plague.app090816registration.Messaging.clients.ReceiveThread;
import com.example.plague.app090816registration.Tabs.MessageHandler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Receiver {
    private static ConcurrentHashMap<String, ConcurrentLinkedQueue<Message>> messages  = new ConcurrentHashMap<>();

    public Receiver(String email, MessageHandler handler) {
        new ReceiveThread(handler, email).start();
    }

    public static ConcurrentHashMap<String, ConcurrentLinkedQueue<Message>> getMessages() {
        return messages;
    }

}
