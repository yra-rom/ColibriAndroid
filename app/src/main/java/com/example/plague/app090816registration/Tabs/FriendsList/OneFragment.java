package com.example.plague.app090816registration.tabs.friendslist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.plague.app090816registration.tabs.FriendInfoActivity;
import com.example.plague.app090816registration.R;
import com.example.plague.app090816registration.connection_defaults.constants.SendKeys;

import java.util.ArrayList;
import java.util.List;

public class OneFragment extends Fragment{
    private static final String TAG = "OneFragment";
    private ListView lvFriends;
    private List<FriendItem> items;
    private FriendAdapter adapter;

    public OneFragment(){}

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

    private void initViews(View v) {
        lvFriends = (ListView) v.findViewById(R.id.frnd_lvFriends);

        lvFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FriendItem friend = (FriendItem) adapter.getItem(position);
                Intent intent = new Intent(getActivity(), FriendInfoActivity.class);
                intent.putExtra(SendKeys.FRIEND_NICK, friend.getNick());
                intent.putExtra(SendKeys.FRIEND_EMAIL, friend.getEmail());
                startActivity(intent);
            }
        });
    }

    private void initList() {
        items = new ArrayList<>();
    }

    private void initAdapter() {
        adapter = new FriendAdapter(getContext(), items);
        lvFriends.setAdapter(adapter);

        new Thread(new GetFriendsThread(items, adapter)).start();

    }

}