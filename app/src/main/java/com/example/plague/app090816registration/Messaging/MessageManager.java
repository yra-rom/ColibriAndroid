package com.example.plague.app090816registration.Messaging;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.example.plague.app090816registration.Messaging.MessagePak.Message;
import com.example.plague.app090816registration.Messaging.MessagePak.MessageBuilder;
import com.example.plague.app090816registration.connection_defaults.Constants.SendKeys;
import com.example.plague.app090816registration.connection_defaults.WhoAmI;
import com.example.plague.app090816registration.connection_defaults.clients.ClientThread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageManager{
    private static final String TAG = "MessageManager";
    private static MessageManager instance = new MessageManager();
    private Thread getThread;

    public static MessageManager getInstance() {
        return instance;
    }
    private MessageManager(){
    }

    private static ConcurrentLinkedQueue<Message> messages = new ConcurrentLinkedQueue<>();

    public void send(final String textMessage, final String to){
        String time = new SimpleDateFormat("hh.mm").format(new Date().getTime());

        HashMap<String,String> map = new HashMap<>();
        map.put(SendKeys.TITLE, SendKeys.MESSAGE_SEND);
        map.put(SendKeys.MESSAGE, textMessage);
        map.put(SendKeys.TIME, time);
        map.put(SendKeys.TO, to);
//        map.put(SendKeys.FROM, WhoAmI.getEmail());

        ClientThread.getInstance().toSend(map);
    }

//    private void checkForReceiveMessages(){
//        new Thread(){
//            @Override
//            public void run() {
//                while (!isInterrupted()){
//                    HashMap mapNewMessage = ClientThread.getInstance().getAnswerFor(SendKeys.MESSAGE_RECEIVED);
//                    if(mapNewMessage != null){
//                        String text = (String) mapNewMessage.get(SendKeys.MESSAGE);
//                        String time = (String) mapNewMessage.get(SendKeys.TIME);
//                        String to = (String) mapNewMessage.get(SendKeys.TO);
//                        String from = (String) mapNewMessage.get(SendKeys.FROM);
//
//                        Message message = new MessageBuilder().message(text).time(time).to(to).from(from).build();
//
//                        if(messages.containsKey(from)){
//                            ConcurrentLinkedQueue queue = messages.get(from);
//                            queue.add(message);
//                        }else{
//                            ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue();
//                            queue.add(message);
//                            messages.put(to, queue);
//                        }
//                    }
//                }
//            }
//        }
//        .start();
//    }

    public void getMessageFrom(final String from){
        getThread = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    HashMap mapNewMessage = ClientThread.getInstance().getAnswerFor(SendKeys.MESSAGE_RECEIVED);
                    if (mapNewMessage != null) {
                        String text = (String) mapNewMessage.get(SendKeys.MESSAGE);
                        String time = (String) mapNewMessage.get(SendKeys.TIME);
                        String to = (String) mapNewMessage.get(SendKeys.TO);
                        String from = (String) mapNewMessage.get(SendKeys.FROM);

                        Message message = new MessageBuilder().message(text).time(time).to(to).from(from).build();
                        messages.add(message);
                    }else{
                        try {
                            Thread.sleep(25);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        getThread.start();
    }

    public void stopGetting(){
        if(getThread != null){
            getThread.interrupt();
            getThread = null;
        }
    }

    public Message getNextMessage(){
        if(messages.isEmpty()){
            return null;
        }
        return messages.poll();
    }
}
