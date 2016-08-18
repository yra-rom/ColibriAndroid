package com.example.plague.app090816registration.Tabs.Friends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.plague.app090816registration.FriendInfoActivity;
import com.example.plague.app090816registration.R;
import com.example.plague.app090816registration.connection_defaults.SendKeys;

import java.util.ArrayList;
import java.util.List;

public class OneFragment extends Fragment{

    private ListView lvFriends;
    private List<FriendItem> items;
    private FriendAdapter adapter;

    public OneFragment() {
        // Required empty public constructor
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
        items.add(new FriendItem("Masha",true));
        items.add(new FriendItem("Masha",false));
        items.add(new FriendItem("Yurii",true));
        items.add(new FriendItem("Yurii2",true));
        items.add(new FriendItem("Yurii3",true));
        items.add(new FriendItem("Yurii4",true));
        items.add(new FriendItem("Yurii5",true));
        items.add(new FriendItem("Yurii6",true));
        items.add(new FriendItem("Yurii7",true));
        items.add(new FriendItem("Yurii8",true));
        items.add(new FriendItem("Yurii9",false));
        items.add(new FriendItem("Yurii10",true));
        items.add(new FriendItem("Yurii11",false));
        items.add(new FriendItem("Yurii12",true));
        items.add(new FriendItem("Yurii13",false));
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