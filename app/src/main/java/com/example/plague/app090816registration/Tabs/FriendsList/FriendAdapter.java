package com.example.plague.app090816registration.Tabs.FriendsList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.plague.app090816registration.R;

import java.util.List;

public class FriendAdapter extends BaseAdapter {

    private List <FriendItem> list;
    private LayoutInflater inflater;

    public FriendAdapter(Context context, List<FriendItem> list) {
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = inflater.inflate(R.layout.tabs_friend_item, viewGroup, false);
        }

        TextView tvName = (TextView) view.findViewById(R.id.itemFrnd_tvName);
        ImageView imOnline = (ImageView) view.findViewById(R.id.itemFrnd_ivOnline);
        tvName.setText(((FriendItem)getItem(i)).getNick());
        imOnline.setVisibility( ((FriendItem)getItem(i)).isOnline() ? View.VISIBLE : View.INVISIBLE );
        return view;
    }
}
