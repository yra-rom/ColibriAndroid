package com.example.plague.app090816registration.clients;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;

public class SignThread extends Thread {
    public static final String TAG = "SignThread";
    public static final String HOST = "192.168.43.142";
    public static final int PORT = 5678;

    private static Socket connection;
    private static ObjectInputStream input;
    private static ObjectOutputStream output;

    private Map map;

    private Boolean answer;

    private int numberOfSendMessages = 0;

    public SignThread(Map map){
        this.map = map;
    }

    @Override
    public void run() {
        Log.d(TAG, "Started.");
        try {
            Log.d(TAG, "About to connect...");
            connection = new Socket(InetAddress.getByName(HOST), PORT);
            Log.d(TAG, "Connected!");
            output = new ObjectOutputStream(connection.getOutputStream());
            input = new ObjectInputStream(connection.getInputStream());
            send();
            answer = (Boolean) input.readObject();
            Log.d(TAG, answer.toString());
            close();
        } catch (IOException e) { e.printStackTrace();} catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void send() {
        try {
            if(map!= null) {
                output.flush();
                output.writeObject(map);
                output.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void close(){
        try {
            input.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Boolean getAnswer() {
        return answer;
    }
}
