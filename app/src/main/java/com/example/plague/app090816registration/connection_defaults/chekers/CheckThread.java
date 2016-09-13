package com.example.plague.app090816registration.connection_defaults.chekers;

import com.example.plague.app090816registration.connection_defaults.clients.ClientThread;
import android.util.Log;
import java.io.IOException;
import java.util.Map;

public class CheckThread extends ClientThread {
    private static final String TAG = "CheckThread";

    private Map map;

    private Boolean answer;
    public Boolean getAnswer() {
        return answer;
    }

    public CheckThread(Map map){
        this.map = map;
    }

    @Override
    protected void read() throws IOException, ClassNotFoundException {
        answer = (Boolean) input.readObject();
        Log.d(TAG, answer.toString());
    }

    @Override
    protected void write() throws IOException {
        if(map!= null) {
            output.flush();
            output.writeObject(map);
            output.flush();
        }
    }

}
