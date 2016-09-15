package com.example.plague.app090816registration.Messaging.clients;

import com.example.plague.app090816registration.Messaging.MessagePak.Message;
import com.example.plague.app090816registration.Messaging.MessagePak.MessageBuilder;
import com.example.plague.app090816registration.Messaging.Receiver;
import com.example.plague.app090816registration.Tabs.MessageHandler;
import com.example.plague.app090816registration.connection_defaults.clients.ClientThread;
import com.example.plague.app090816registration.connection_defaults.Constants.SendKeys;

import java.io.EOFException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReceiveThread extends ClientThread {
    private static final String TAG = "ReceiveThread";

    private String whoAmI;
    private MessageHandler handler;

    public ReceiveThread(MessageHandler handler, String whoAmI) {
        this.handler = handler;
        this.whoAmI = whoAmI;
    }

    @Override
    public void run() {
//        while(!isInterrupted()) {
//            super.run();
//        }
        try {
            initConnection();
            initStreams();
            while (!isInterrupted()) {
                write();
                read();
            }
        }catch (ClassNotFoundException | IOException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void write() throws IOException {
        while (whoAmI == null){
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        HashMap<String, String> map = new HashMap<>();
        map.put(SendKeys.TITLE, SendKeys.WHO_AM_I);
        map.put(SendKeys.WHO, whoAmI);

        output.flush();
        output.writeObject(map);
        output.flush();
    }

    @Override
    protected void read() throws IOException, ClassNotFoundException {
        Object o  = input.readObject();
        if(o instanceof Map){
            HashMap<String, String> map  = (HashMap<String,String>) o;
            if(map.get(SendKeys.TITLE).equals(SendKeys.MESSAGE_RECEIVED)){
                String text = map.get(SendKeys.MESSAGE);
                String from = map.get(SendKeys.FROM);
                String time = map.get(SendKeys.TIME);

                Message message = new MessageBuilder().message(text).time(time).from(from).to(whoAmI).build();
//                Receiver.getMessages().put(from,message);

                android.os.Message msg = handler.obtainMessage(SendKeys.NEW_MESSAGE_STATUS, message);
                handler.sendMessage(msg);
            }
        }
    }
}
