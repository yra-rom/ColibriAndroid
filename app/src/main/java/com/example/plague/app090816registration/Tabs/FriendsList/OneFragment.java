package com.example.plague.app090816registration.Tabs.FriendsList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.plague.app090816registration.Tabs.FriendInfoActivity;
import com.example.plague.app090816registration.R;
import com.example.plague.app090816registration.Tabs.MessageHandler;
import com.example.plague.app090816registration.connection_defaults.Constants.SendKeys;

import java.util.ArrayList;
import java.util.List;

public class OneFragment extends Fragment{

    private ListView lvFriends;
    private List<FriendItem> items;
    private FriendAdapter adapter;
    private MessageHandler handler;


    public OneFragment(){
    }

    public void setHandler(MessageHandler handler) {
        this.handler = handler;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.tabs_fragment_1_friends, container, false);
        initViews(v);
        initList();
        initAdapter();
        return v;
    }

    private void initAdapter() {
        adapter = new FriendAdapter(getContext(), items);

        //TO DO

        /* Only for debug*/
        items.add(new FriendItemBuilder().name("Masha").online(true).build() );
        items.add(new FriendItemBuilder().name("Masha").online(false).build() );
        items.add(new FriendItemBuilder().name("Yurii").online(true).build() );
        items.add(new FriendItemBuilder().name("Yurii2").online(true).build() );
        items.add(new FriendItemBuilder().name("Yurii3").online(true).build() );
        items.add(new FriendItemBuilder().name("Yurii4").online(true).build() );
        items.add(new FriendItemBuilder().name("Yurii5").online(true).build() );
        items.add(new FriendItemBuilder().name("Yurii6").online(true).build() );
        items.add(new FriendItemBuilder().name("Yurii7").online(true).build() );
        items.add(new FriendItemBuilder().name("Yurii8").online(true).build() );
        items.add(new FriendItemBuilder().name("Yurii9").online(false).build() );
        items.add(new FriendItemBuilder().name("Yurii10").online(true).build() );
        items.add(new FriendItemBuilder().name("Yurii11").online(false).build() );
        items.add(new FriendItemBuilder().name("Yurii12").online(true).build() );
        items.add(new FriendItemBuilder().name("Yurii13").online(true).build() );
        /*---------------------------------------------------------------*/

        lvFriends.setAdapter(adapter);
    }

    private void initList() {
        items = new ArrayList<FriendItem>();
    }

    private void initViews(View v) {
        lvFriends = (ListView) v.findViewById(R.id.frnd_lvFriends);
        lvFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FriendItem friend = (FriendItem) adapter.getItem(position);
                Log.d("OneFragment", friend.getName());
                //TO DO
                //Activity when friend is choosen
            }
        });

        lvFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FriendItem friend = (FriendItem) adapter.getItem(position);
                Intent intent = new Intent(getActivity(), FriendInfoActivity.class);
                intent.putExtra(SendKeys.NICK, friend.getName());
                startActivity(intent);
            }
        });
    }


}