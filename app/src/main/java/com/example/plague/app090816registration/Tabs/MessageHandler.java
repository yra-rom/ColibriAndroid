package com.example.plague.app090816registration.Tabs;

import android.os.Handler;
import android.os.Message;

import com.example.plague.app090816registration.Messaging.Receiver;
import com.example.plague.app090816registration.Messaging.clients.Status;

import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageHandler extends Handler {
    private int WHERE_SHOW;

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case Status.MESSAGE_RECEIVED:
                if (WHERE_SHOW == Status.ON_SCREEN) {
                    com.example.plague.app090816registration.Messaging.MessagePak.Message message = (com.example.plague.app090816registration.Messaging.MessagePak.Message) msg.obj;
                    if(Receiver.getMessages().containsKey(message.getTo())){
                        ConcurrentLinkedQueue<com.example.plague.app090816registration.Messaging.MessagePak.Message> queue = Receiver.getMessages().get(message.getTo());
                        queue.add(message);
                    }else{
                        ConcurrentLinkedQueue<com.example.plague.app090816registration.Messaging.MessagePak.Message> queue = new ConcurrentLinkedQueue<>();
                        queue.add(message);
                        Receiver.getMessages().put(message.getTo(), queue);
                    }
                    notify();
                } else if (WHERE_SHOW == Status.IN_SERVICE) {
                    showInService();
                }
                break;
            case Status.CH_WHERE_SHOW:
                WHERE_SHOW = msg.arg1;
                if(WHERE_SHOW == Status.IN_SERVICE){

                }else if (WHERE_SHOW == Status.ON_SCREEN){

                }
                break;
        }
    }



    private void showInService() {

    }
}
