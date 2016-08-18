package com.example.plague.app090816registration.Messaging.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.plague.app090816registration.R;
import com.example.plague.app090816registration.connection_defaults.SendKeys;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MessagesActivity extends AppCompatActivity {

    private EditText etSend;
    private Button btnSend;
    private LinearLayout llReceived;

    private String to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_main);
        initTitle();

        initViews();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
    }

    private void showSendMsg(String msg) {
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.message_received, null, true);
        ViewGroup.LayoutParams lp = v.getLayoutParams();

        TextView tvMsg = (TextView) v.findViewById(R.id.rcd_tvMsg);
        TextView tvMsgInfo = (TextView) v.findViewById(R.id.rcd_tvMsgInfo);

        tvMsg.setText(msg);

        String time = new SimpleDateFormat("hh:mm").format(new Date());
        tvMsgInfo.setText(time);

        llReceived.addView(v);
    }

    public void showReceivedMsg(String msg){
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
            String msg = etSend.getText().toString().trim();
            if(msg != "") {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(to, msg);
                editor.commit();
            }
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String msg = preferences.getString(to, "");
        if(msg != ""){
            etSend.setText(msg);
        }
    }
}