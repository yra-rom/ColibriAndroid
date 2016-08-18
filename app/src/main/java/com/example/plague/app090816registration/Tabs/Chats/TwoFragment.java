package com.example.plague.app090816registration.Tabs.Chats;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.plague.app090816registration.Messaging.activities.MessagesActivity;
import com.example.plague.app090816registration.R;
import com.example.plague.app090816registration.connection_defaults.SendKeys;

import java.util.ArrayList;
import java.util.List;

public class TwoFragment extends Fragment {

    private ListView lvChats;
    private List<ChatItem> items;
    private ChatsAdapter adapter;

    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.tabs_fragment_2_chats, container, false);
        initViews(v);
        initList();
        initAdapter();
        return v;
    }

    private void initAdapter() {
        adapter = new ChatsAdapter(getContext(), items);

        //TO DO
        //connect to db and get your friends

        /* Only for debug*/
        items.add(new ChatItem("Masha", "some text from masha","21.52"));
        items.add(new ChatItem("Yurii", "some text from yurii","12.29"));
        items.add(new ChatItem("Yurii2","some text from yurii" ,"00.01"));
        items.add(new ChatItem("Yurii2","some text from yurii" ,"00.01"));
        items.add(new ChatItem("Yurii2","some text from yurii" ,"00.01"));
        items.add(new ChatItem("Yurii2","some text from yurii" ,"00.01"));
        items.add(new ChatItem("Yurii2","some text from yurii" ,"00.01"));
        items.add(new ChatItem("Yurii2","some text from yurii" ,"00.01"));
        items.add(new ChatItem("Yurii2","some text from yurii" ,"00.01"));
        items.add(new ChatItem("Yurii2","some text from yurii" ,"00.01"));
        items.add(new ChatItem("Yurii2","some text from yurii" ,"00.01"));
        items.add(new ChatItem("Yurii2","some text from yurii" ,"00.01"));
        items.add(new ChatItem("Yurii2","some text from yurii" ,"00.01"));
        items.add(new ChatItem("Yurii2","some text from yurii" ,"00.01"));
        items.add(new ChatItem("Yurii2","some text from yurii" ,"00.01"));
        items.add(new ChatItem("Yurii3","some text from yurii" ,"00.01"));
        /*---------------------------------------------------------------*/

        lvChats.setAdapter(adapter);
    }

    private void initList() {
        items = new ArrayList<ChatItem>();
    }

    private void initViews(View v) {
        lvChats = (ListView) v.findViewById(R.id.chat_lvChat);
        lvChats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChatItem chatItem = (ChatItem) adapter.getItem(position);
                Intent intent = new Intent(getActivity(), MessagesActivity.class);
                intent.putExtra(SendKeys.NICK, chatItem.getNick());
                startActivity(intent);
                //To DO
                //Activity when last chat was chosen
                //Figure a method to save all history in the phone
            }
        });
    }

}