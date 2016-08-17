package com.example.plague.app090816registration.Messaging.clients;

import android.util.Log;

import com.example.plague.app090816registration.WhoAmI;
import com.example.plague.app090816registration.connection_defaults.Ports;
import com.example.plague.app090816registration.connection_defaults.SendKeys;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ReceiveThread extends Thread {
    private static final String TAG = "ReceiveThread";
    public static final String HOST = Ports.HOST;
    private static final int PORT = Ports.RECEIVE_MESSAGE;

    private static Socket connection;
    private static ObjectInputStream input;
    private static ObjectOutputStream output;

    private Message message;
    private WhoAmI whoAmI;

    public ReceiveThread(WhoAmI whoAmI) {
        this.whoAmI = whoAmI;
    }

    @Override
    public void run() {
        while(!isInterrupted()) {
            try {
                initConnection();
                initStreams();
                writeWho();
                readMessage();
                close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeWho() throws IOException {
        while (whoAmI == null){
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        HashMap<String, String> map = new HashMap<>();
        map.put(SendKeys.WHO, whoAmI.getEmail());

        output.flush();
        output.writeObject(map);
        output.flush();
    }

    private void readMessage() throws IOException, ClassNotFoundException {
        Object o  = input.readObject();
        if(o instanceof Map){
            HashMap<String, String> map  = (HashMap) o;
            if(map.get(SendKeys.TITLE).equals(SendKeys.MESSAGE_RECEIVED)){
                String text = map.get(SendKeys.MESSAGE);
                String to = map.get(SendKeys.TO);
                String from = map.get(SendKeys.FROM);
                String time = map.get(SendKeys.TIME);

                Message message = new Message(text, time, from, to);
                Messaging.getMessages().put(from,message);

            }
        }
    }


    private void initStreams() throws IOException {
        output = new ObjectOutputStream(connection.getOutputStream());
        input = new ObjectInputStream(connection.getInputStream());
    }

    private void initConnection() throws IOException {
        Log.d(TAG, "Started.");
        Log.d(TAG, "About to connect...");
        connection = new Socket(InetAddress.getByName(HOST), PORT);
        Log.d(TAG, "Connected!");
    }

    public void close(){
        try {
            input.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
