package com.example.plague.app090816registration.connection_defaults.clients;

import android.util.Log;

import com.example.plague.app090816registration.LogIn.activities.LogInActivity;
import com.example.plague.app090816registration.connection_defaults.Constants.SendKeys;

import java.io.IOException;
import java.util.HashMap;

public class CheckConnectionThread extends ClientThread {
    public static final String TAG = "CheckConnectionThread";
    public boolean currentState = false;
    private Thread refreshConnectionState;

    public CheckConnectionThread(final LogInActivity activity){
        refreshConnectionState = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    if (currentState) {
                        activity.setConnectionON();
                        try {
                            Thread.sleep(15000);
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
        try {
            initConnection();
            initStreams();

            while (!isInterrupted()){
                write();
                read();
            }

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        if(isInterrupted()) {
            refreshConnectionState.interrupt();
        }
    }

    @Override
    protected void write() throws IOException, ClassNotFoundException {
        HashMap map = new HashMap<>();
        map.put(SendKeys.TITLE, SendKeys.I_AM_CONNECTED);
        output.flush();
        output.writeObject(map);
        output.flush();
    }

    @Override
    protected void read() throws IOException, ClassNotFoundException {
        currentState = false;
        input.readObject();
        currentState = true;
    }
}
