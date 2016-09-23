package com.example.plague.app090816registration.Tabs.FriendsList;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.plague.app090816registration.connection_defaults.clients.ClientThread;
import com.example.plague.app090816registration.connection_defaults.Constants.SendKeys;

import java.util.HashMap;
import java.util.List;

public class GetFriendsThread extends AppCompatActivity implements Runnable {
    private static final String TAG = "GetFriendsThread";
    private List<FriendItem> items;
    private FriendAdapter adapter;

    private boolean interrupted;
    public boolean isInterrupted(){
        return interrupted;
    }
    public void interrupt(){
        interrupted = true;
    }

    public GetFriendsThread(List<FriendItem> items, FriendAdapter adapter) {
        this.adapter = adapter;
        this.items = items;
        interrupted = false;
    }

    @Override
    public void run() {
        while(!isInterrupted()) {
            updateList();
            sleep(3000);
        }
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void updateList(){
        HashMap <String, String> mapSend = new HashMap<>();
        mapSend.put(SendKeys.TITLE, SendKeys.GET_FRIENDS);

        ClientThread.getInstance().toSend(mapSend);

        HashMap mapReceive = null;
        while (mapReceive == null) {
            mapReceive = ClientThread.getInstance().getAnswerFor(SendKeys.GET_FRIENDS);
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                items.clear();
                adapter.notifyDataSetChanged();
                adapter.notifyDataSetInvalidated();
            }
        });

        Integer count = Integer.valueOf( (String) mapReceive.get(SendKeys.COUNT_FRIENDS) );
        Log.d(TAG, "We have " + count + " friends");
        if(count != null && count > 0) {
            for (int i = 0; i < count; ++i) {

                String nick = (String) mapReceive.get(SendKeys.FRIEND_NICK + i);
                String email = (String) mapReceive.get(SendKeys.FRIEND_EMAIL + i);
                String lastOnline = (String) mapReceive.get(SendKeys.FRIEND_LAST_ONLINE + i);

                Log.d(TAG, "Friend " + i + "email: " + email + "nick: " + nick);

                FriendItem item = new FriendItemBuilder().name(nick).email(email).online(true).build();
                items.add(item);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        adapter.notifyDataSetInvalidated();
                    }
                });
            }
        }
    }
}

