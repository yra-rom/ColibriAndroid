package com.example.plague.app090816registration.Tabs.Chats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.plague.app090816registration.R;

import java.util.List;

public class ChatsAdapter extends BaseAdapter {

    private List <ChatItem> list;
    private LayoutInflater inflater;

    public ChatsAdapter(Context context, List<ChatItem> list) {
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ChatItem getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = inflater.inflate(R.layout.tabs_chat_item, viewGroup, false);
        }

        ImageView imOnline = (ImageView) view.findViewById(R.id.lchat_ivAvatar);
        TextView tvNick = (TextView) view.findViewById(R.id.lchat_tvNick);
        TextView tvLast = (TextView) view.findViewById(R.id.lchat_tvLastMsg);
        TextView tvTime = (TextView) view.findViewById(R.id.lchat_tvTime);

        tvNick.setText(((ChatItem)getItem(i)).getNick());
        tvLast.setText(((ChatItem)getItem(i)).getLast());
        tvTime.setText(((ChatItem)getItem(i)).getTime());

        return view;
    }

}
