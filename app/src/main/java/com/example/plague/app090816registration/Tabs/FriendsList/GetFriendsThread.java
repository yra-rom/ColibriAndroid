package com.example.plague.app090816registration.tabs.friendslist;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.plague.app090816registration.connection_defaults.clients.ClientThread;
import com.example.plague.app090816registration.connection_defaults.constants.SendKeys;
import com.example.plague.app090816registration.connection_defaults.friend.Friend;
import com.example.plague.app090816registration.connection_defaults.packet.Packet;

import java.util.ArrayList;
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

        Packet packetOut = new Packet(SendKeys.GET_FRIENDS, "nothing");

        ClientThread.getInstance().toSend(packetOut);

        Packet packetIn = null;
        while (packetIn == null) {
            packetIn = ClientThread.getInstance().getAnswerFor(SendKeys.GET_FRIENDS);
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                items.clear();
                adapter.notifyDataSetChanged();
                adapter.notifyDataSetInvalidated();
            }
        });

        ArrayList<Friend> friends = (ArrayList<Friend>) packetIn.getMessage();

        Log.d(TAG, "We have " + friends.size() + " friends");
        if(friends.size() > 0) {

            for(Friend friend : friends){
                String nick = friend.getNick();
                String email = friend.getEmail();
                String lastOnline = friend.getLastOnline();

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

