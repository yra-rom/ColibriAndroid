package com.example.plague.app090816registration.Messaging.clients;
import com.example.plague.app090816registration.Messaging.MessagePak.Message;
import com.example.plague.app090816registration.connection_defaults.clients.ClientThread;
import com.example.plague.app090816registration.connection_defaults.Constants.SendKeys;

import java.io.IOException;

import java.util.HashMap;

public class SendThread extends ClientThread {
    private static final String TAG = "SendThread";

    private Message message;

    private boolean confirmed = false;
    public boolean isConfirmed() {
        return confirmed;
    }

    public SendThread(Message message) {
        this.message = message;
    }

    @Override
    protected void write() throws IOException {
        HashMap<String, String> map = new HashMap<>();
        map.put(SendKeys.TITLE, SendKeys.MESSAGE_SEND);

        map.put(SendKeys.MESSAGE, message.getMessage());
        map.put(SendKeys.TIME, message.getTime());
        map.put(SendKeys.FROM, message.getFrom());
        map.put(SendKeys.TO, message.getTo());

        output.flush();
        output.writeObject(map);
        output.flush();
    }

    @Override
    protected void read() throws IOException, ClassNotFoundException {
        Boolean answer = (Boolean) input.readObject();
        if(answer){
            confirmed = true;
        }
    }
}
