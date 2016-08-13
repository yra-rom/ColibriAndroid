package com.example.plague.app090816registration.clients;

import android.util.Log;

import com.example.plague.app090816registration.LogInAndRegistration.LogInActivity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class CheckConnectionThread extends Thread {
    public static final String HOST = "192.168.43.142";
    //    public static final String HOST = "192.168.2.4";
    public static final int PORT = 5679;
    public static final String TAG = "CheckConnectionThread";

    private static Socket connection;
    private static ObjectInputStream input;
    private static ObjectOutputStream output;

    public boolean currentState = false;

    private LogInActivity activity;

    Thread refreshConnectionState;

    public CheckConnectionThread(LogInActivity activity){
        this.activity = activity;
        refreshConnectionState = new Thread( new Runnable() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    if (currentState) {
                        activity.setConnectionON();
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.d(TAG, "No Connection");
                        activity.setConnectionOFF();
                    }
                }
            }
        });
        refreshConnectionState.start();
    }

    @Override
    public void run() {
        while(!isInterrupted()) {
            try {
                initConnection();
                initStreams();
                ask();
                receive();
                close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void receive() throws IOException, ClassNotFoundException {
//        activity.setConnectionOFF();
        currentState = false;
        input.readObject();
        currentState = true;
//        activity.setConnectionON();
    }

    private void ask() throws IOException {
        output.flush();
        output.writeObject(true);
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

    public void close(){
        try {
            input.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
