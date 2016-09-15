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

import com.example.plague.app090816registration.Messaging.MessagePak.Message;
import com.example.plague.app090816registration.Messaging.MessagePak.MessageBuilder;
import com.example.plague.app090816registration.Messaging.Receiver;
import com.example.plague.app090816registration.Messaging.clients.SendThread;
import com.example.plague.app090816registration.R;
import com.example.plague.app090816registration.Tabs.MessageHandler;
import com.example.plague.app090816registration.connection_defaults.Constants.SendKeys;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessagesActivity extends AppCompatActivity {

    private EditText etSend;
    private Button btnSend;
    private LinearLayout llReceived;
    private String to;

    private MessageHandler handler;

    Thread messageThread = new Thread() {
        @Override
        public void run() {
            while (!isInterrupted()) {//isInteruted
                try {
                    ConcurrentLinkedQueue<Message> queue = Receiver.getMessages().get(to);
                    if(queue != null) {
                        Message msg = queue.poll();
                        if (msg != null) {
                            String text = msg.getMessage();
                            showReceivedMsg(text);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_main);
        initTitle();

        initViews();
        initHandler();
        notifyHandler();
        ActionBar actionBar = getSupportActionBar();
        if(actionBar !=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        startWaitingForMessage();
    }

    private void initHandler() {
//        Intent intent = getIntent();
//        handler = intent.getParcelableExtra(SendKeys.HANDLER);
    }


    private void notifyHandler() {
//        if(handler != null){
//            Message msg = handler.obtainMessage(StatusINT.CH_WHERE_SHOW, StatusINT.ON_SCREEN, 0, null);
//            handler.sendMessage(msg);
//        }
    }

    private void startWaitingForMessage() {
        messageThread.start();
    }

    private void stopWaitingForMessage() {
        messageThread.interrupt();
    }

    private void initTitle() {
        Intent intent = getIntent();
        to = intent.getStringExtra(SendKeys.NICK);
        setTitle(to);
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
        String msg = etSend.getText().toString().trim();
        showSendMsg(msg);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String from =  prefs.getString(SendKeys.EMAIL, "");
        String time = new SimpleDateFormat("hh.mm").format(new Date().getTime());

        Message message = new MessageBuilder().message(msg).time(time).from(from).to(to).build();
        SendThread sendThread = new SendThread(message);
        sendThread.start();
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

    public void showReceivedMsg(final String message) throws InterruptedException {
        wait();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.message_received, null, true);
                ViewGroup.LayoutParams lp = v.getLayoutParams();

                TextView tvMsg = (TextView) v.findViewById(R.id.rcd_tvMsg);
                TextView tvMsgInfo = (TextView) v.findViewById(R.id.rcd_tvMsgInfo);

                tvMsg.setText(message);

                String time = new SimpleDateFormat("hh:mm").format(new Date());
                tvMsgInfo.setText(time);

                llReceived.addView(v);
            }
        });
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
        if(msg != "") {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(to, msg);
            editor.apply();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String msg = preferences.getString(to, "");
        if(!msg.equals("")){
            etSend.setText(msg);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveLastWords();
        stopWaitingForMessage();
    }

    public String getWho() {
        return to;
    }
}