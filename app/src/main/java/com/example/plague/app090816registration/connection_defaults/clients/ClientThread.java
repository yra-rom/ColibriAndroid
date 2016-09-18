package com.example.plague.app090816registration.connection_defaults.clients;
import android.util.Log;

import com.example.plague.app090816registration.connection_defaults.Constants.Client_Info;
import com.example.plague.app090816registration.connection_defaults.Constants.SendKeys;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientThread extends Thread {
    private static final String TAG = "ClientThread";
    private static final String HOST = Client_Info.HOST;
    private static final int PORT = Client_Info.MAIN_PORT;

    protected static Socket connection;
    protected ObjectInputStream input;
    protected ObjectOutputStream output;

    private static ClientThread instance = new ClientThread();
    public static ClientThread getInstance(){
        return instance;
    }

    private ClientThread(){
        start();
    }

    private static final ConcurrentLinkedQueue< HashMap<String, String> > questions = new ConcurrentLinkedQueue<>();
    private static final List<HashMap<String, String>> answers = Collections.synchronizedList(new ArrayList<HashMap<String, String>>());


    public void toSend(HashMap<String, String> map){
        if(map != null) {
            questions.add(map);
        }
    }

    public HashMap getAnswerFor(String key){
        for (HashMap map : answers){
            if(key.equals(map.get(SendKeys.TITLE))){
                answers.remove(map);
                return map;
            }
        }
        return null;
    }

    private void initStreams() throws IOException {
        output = new ObjectOutputStream(connection.getOutputStream());
        input = new ObjectInputStream(connection.getInputStream());
    }

    protected void initConnection() throws IOException {
        Log.d(TAG, "Started.");
        Log.d(TAG, "About to connect...");
        connection = new Socket(InetAddress.getByName(HOST), PORT);
        Log.d(TAG, "Connected!");
    }

    public void close() {
        try {
            input.close();
            output.close();
            connection.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            initConnection();
            initStreams();

            write();
            read();

        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            close();
        }
    }

    private void write(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!isInterrupted()) {
                    if (!questions.isEmpty()) {
                        try {
                            output.flush();
                            HashMap map = questions.poll();
                            output.writeObject(map);
                            output.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else {
                        Thread.yield();
                    }
                }
            }
        }).start();
    }

    private void read(){
        while (!isInterrupted()) {
            try {
                Object o = input.readObject();
                if (o instanceof HashMap) {
                    HashMap<String, String> map = (HashMap<String, String>) o;
                    answers.add(map);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConnected(){
        return connection != null && connection.isConnected();
    }
}
