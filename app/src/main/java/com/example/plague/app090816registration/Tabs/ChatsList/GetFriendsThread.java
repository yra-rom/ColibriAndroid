package com.example.plague.app090816registration.Tabs.ChatsList;

import com.example.plague.app090816registration.connection_defaults.clients.ClientThread;
import com.example.plague.app090816registration.connection_defaults.Constants.SendKeys;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GetFriendsThread extends ClientThread {
    private static final String TAG = "GetFriendsThread";

    private List<ChatItem> items;
    public GetFriendsThread(List<ChatItem> items) {
        this.items = items;
    }

    @Override
    public void run() {
        super.run();
    }

    protected void read() throws IOException, ClassNotFoundException {
        HashMap map = (HashMap<String, String>) input.readObject();
        if(map.get(SendKeys.TITLE).equals(SendKeys.GET_FRIENDS)){
            Integer count = Integer.valueOf( (String) map.get(SendKeys.COUNT_FRIENDS) );
            if(count != null && count > 0){
                for (int  i = 0; i < count; ++i){

                    String nick = (String) map.get(SendKeys.FRIEND_NICK + i);
                    String email = (String) map.get(SendKeys.FRIEND_EMAIL + i);
                    String lastOnline = (String) map.get(SendKeys.FRIEND_LAST_ONLINE + i);

                    ChatItem chatItem = new ChatItemBuilder().
                                                            nick(nick).
                                                            email(email).
                                                            last("some last message").
                                                            time(lastOnline).build();

                    items.add(chatItem);
                }
            }
        }
    }

    protected void write() throws IOException {
        HashMap <String, String> map = new HashMap<>();
        map.put(SendKeys.TITLE, SendKeys.GET_FRIENDS);

        output.flush();
        output.writeObject(map);
        output.flush();
    }
}

