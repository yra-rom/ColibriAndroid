package com.example.plague.app090816registration.connection_defaults.clients;

import android.util.Log;

import com.example.plague.app090816registration.LogIn.activities.LogInActivity;

public class CheckConnectionThread extends Thread {
    private static final String TAG = "CheckConnectionThread";
    private LogInActivity activity;

    public CheckConnectionThread(final LogInActivity activity){
        this.activity = activity;
        start();
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            if (ClientThread.getInstance().isConnected()) {
                activity.setConnectionON();
            } else {
                Log.d(TAG, "No Connection");
                activity.setConnectionOFF();
            }
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
