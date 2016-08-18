package com.example.plague.app090816registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.plague.app090816registration.Messaging.activities.MessagesActivity;


public class FriendInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivAvatar;
    private TextView tvLastOnline;
    private TextView tvNick;
    private Button btnCall;
    private Button btnChat;

    private String nick;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initTitle();
        initViews();
        fillViews();
    }

    private void fillViews() {
        //TO DO
        //connect to DB and get
        //avatar, last time online
    }

    private void initViews() {
        ivAvatar = (ImageView) findViewById(R.id.frndInfo_ivAvatar);

        tvLastOnline = (TextView) findViewById(R.id.frndInfo_tvLastOnline);
        tvNick = (TextView) findViewById(R.id.frndInfo_tvNick);

        btnCall = (Button) findViewById(R.id.frndInfo_btnCall);
        btnChat = (Button) findViewById(R.id.frndInfo_btnChat);

        btnCall.setOnClickListener(this);
        btnChat.setOnClickListener(this);
    }

    private void initTitle() {
        Intent intent = getIntent();
        nick = intent.getStringExtra("NICK");
        setTitle(nick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.frndInfo_btnChat:
                //TO DO
                //test this
                Intent intent = new Intent(this, MessagesActivity.class);
                intent.putExtra("NICK", nick);
                startActivity(intent);
                break;
            case R.id.frndInfo_btnCall:
                //TO DO
                //calling activity
                break;
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) { //app icon in action bar clicked; go back
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
