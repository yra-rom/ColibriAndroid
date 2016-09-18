package com.example.plague.app090816registration.Messaging.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.plague.app090816registration.Messaging.MessageManager;
import com.example.plague.app090816registration.Messaging.MessagePak.Message;
import com.example.plague.app090816registration.R;
import com.example.plague.app090816registration.connection_defaults.Constants.SendKeys;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MessagesActivity extends AppCompatActivity {

    private EditText etSend;
    private Button btnSend;
    private LinearLayout llReceived;
    private String friend_nick;
    private String friend_email;
    private Thread gettingMeesage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_main);
        getExtras();
        initTitle();
        initViews();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    private void getExtras() {
        Intent intent = getIntent();
        friend_nick = intent.getStringExtra(SendKeys.FRIEND_NICK);
        friend_email = intent.getStringExtra(SendKeys.FRIEND_EMAIL);
    }

    private void initTitle() {
        setTitle(friend_nick);
    }

    private void initViews() {
        etSend = (EditText) findViewById(R.id.msg_etMsg);
        btnSend = (Button) findViewById(R.id.msg_btnSend);
        llReceived = (LinearLayout) findViewById(R.id.msg_llReceived);

        btnSend.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(etSend.getText().toString().equals("")){ return; }
                sendMessage();
                etSend.setText("");
            }
        } );
    }

    private void sendMessage() {
        String textMessage = etSend.getText().toString().trim();
        showSendMsg(textMessage);
        MessageManager.getInstance().send(textMessage, friend_email);
    }

    private void showSendMsg(String msg) {
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.message_send, null, true);
        ViewGroup.LayoutParams lp = v.getLayoutParams();

        TextView tvMsg = (TextView) v.findViewById(R.id.snd_tvMsg);
        TextView tvMsgInfo = (TextView) v.findViewById(R.id.snd_tvMsgInfo);

        tvMsg.setText(msg);

        String time = new SimpleDateFormat("hh:mm").format(new Date());
        tvMsgInfo.setText(time);

        llReceived.addView(v);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) { //app icon in action bar clicked; go back
            saveLastWords();
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveLastWords() {
        String msg = etSend.getText().toString().trim();
        if(msg.equals("")) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(friend_email, msg);
            editor.apply();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String msg = preferences.getString(friend_email, "");
        if(!msg.equals("")){
            etSend.setText(msg);
        }
        receiveMessage();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveLastWords();
        stopGettingMessages();
    }

    private void stopGettingMessages() {
        if(gettingMeesage != null){
            gettingMeesage.interrupt();
            gettingMeesage = null;
        }
    }

    private void receiveMessage(){
        MessageManager.getInstance().getMessageFrom(friend_email);

        gettingMeesage = new Thread() {
            @Override
            public void run() {
                while(!isInterrupted()) {
                    Message message = null;
                    while (message == null) {
                        message = MessageManager.getInstance().getNextMessage();
                    }
                    showReceivedMsg(message);
                }
            }
        };

        gettingMeesage.start();
    }

    public void showReceivedMsg(final Message message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.message_received, null, true);
                ViewGroup.LayoutParams lp = v.getLayoutParams();

                TextView tvMsg = (TextView) v.findViewById(R.id.rcd_tvMsg);
                TextView tvMsgInfo = (TextView) v.findViewById(R.id.rcd_tvMsgInfo);

                tvMsg.setText(message.getMessage());

                String time = message.getTime();
                tvMsgInfo.setText(time);

                llReceived.addView(v);
            }
        });
    }
}