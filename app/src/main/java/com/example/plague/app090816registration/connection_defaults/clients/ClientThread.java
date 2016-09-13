package com.example.plague.app090816registration.connection_defaults.clients;

import android.util.Log;

import com.example.plague.app090816registration.connection_defaults.Constants.Client_Info;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

abstract public class ClientThread extends Thread {
    private static final String HOST = Client_Info.HOST;
    private static final int PORT = Client_Info.MAIN_PORT;
    private static final String TAG = "ClientThread";

    private Socket connection;
    protected ObjectInputStream input;
    protected ObjectOutputStream output;

    @Override
    public void run() {
        try {
            initConnection();
            initStreams();
            write();
            read();
            close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
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

    public void close() {
        try {
            input.close();
            output.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    abstract protected void write() throws IOException, ClassNotFoundException;
    abstract protected void read() throws IOException, ClassNotFoundException;

}
