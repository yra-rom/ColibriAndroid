package com.example.plague.app090816registration.Messaging.clients;

import android.util.Log;

import com.example.plague.app090816registration.connection_defaults.Ports;
import com.example.plague.app090816registration.connection_defaults.SendKeys;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;

public class SendThread extends Thread {
    private static final String TAG = "SendThread";
    private static final String HOST = Ports.HOST;
    private static final int PORT = Ports.SEND_MESSAGE;

    private static Socket connection;
    private static ObjectInputStream input;
    private static ObjectOutputStream output;
    private Message message;

    private boolean confirmed = false;

    public SendThread(Message message) {
        this.message = message;
    }

    @Override
    public  void run() {
        try {
            initConnection();
            initStreams();

            sendMessage();
            receiveAnsw();

            close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void receiveAnsw() throws IOException, ClassNotFoundException {
        Boolean answ = (Boolean) input.readObject();
        if(answ){confirmed = true;}
    }

    private void sendMessage() throws IOException {
        HashMap<String, String> map = new HashMap<>();
        map.put(SendKeys.TITLE, SendKeys.MESSAGE_SEND);
        map.put(SendKeys.MESSAGE, message.getMessage());
        map.put(SendKeys.TO, message.getTo());
        map.put(SendKeys.FROM, message.getFrom());
        map.put(SendKeys.TIME, message.getTime());

        output.flush();
        output.writeObject(map);
        output.flush();
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

    public boolean isConfirmed() {
        return confirmed;
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
