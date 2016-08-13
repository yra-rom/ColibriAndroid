package com.example.plague.app090816registration.LogInAndRegistration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.plague.app090816registration.R;
import com.example.plague.app090816registration.clients.CheckConnectionThread;
import com.example.plague.app090816registration.clients.LogInThread;
import com.example.plague.app090816registration.clients.SendKeys;

import java.util.HashMap;
import java.util.Map;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG = "LogInActivity";

    private EditText etEmail;
    private EditText etPassword;
    private Button btnSignIn;
    private TextView tvRegister;
    private CheckBox cbKeepLogged;
    private SharedPreferences sharedPrefs;
    private TextView tvRegSucc;
    private TextView tvConnInfo;

    private CheckConnectionThread chConn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        initViews();
    }

    @Override
    protected void onPause() {
        chConn.interrupt();
        super.onPause();
    }

    private void initViews() {
        etEmail = (EditText) findViewById(R.id.sign_etEmail);
        etPassword = (EditText) findViewById(R.id.sign_etPassword);
        btnSignIn = (Button) findViewById(R.id.sign_btnSignIn);
        tvRegister = (TextView) findViewById(R.id.tvRegister);
        cbKeepLogged = (CheckBox) findViewById(R.id.sign_cbKeepLogged);
        tvRegSucc = (TextView) findViewById(R.id.sign_tvRegSucc);
        tvConnInfo = (TextView) findViewById(R.id.sign_tvConnectionInfo);

        Log.d(TAG, String.valueOf(etEmail.getHintTextColors()));

        chConn = new CheckConnectionThread(this);
        chConn.start();

        btnSignIn.setOnClickListener(this);
        tvRegister.setOnClickListener(this);


        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                etEmail.setTextColor(Color.BLACK);
                etEmail.setHintTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDefaultHint));
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                etPassword.setTextColor(Color.BLACK);
                etPassword.setHintTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDefaultHint));
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_btnSignIn:
                logInUser();
                break;
            case R.id.tvRegister:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivityForResult(intent, 1);
    }

    private void logInUser() {
        String email = etEmail.getText().toString();
        String pass = etPassword.getText().toString();
        if(checkEmail(email)){
            Log.d(TAG, "Server checked email: email is correct");
            if(checkPassword(email, pass)){
                Log.d(TAG, "Server checked pass: pass is correct");
                //if user and password are correct and
                if(cbKeepLogged.isChecked()){
                    //remember users nick and pass
                    rememberUser(email, pass);
                }
                Log.d(TAG,"Imitating that user successfully logged in");
                //TO DO Activity when is logged in
            }else{
                Log.d(TAG, "Server checked name: name is NOT correct");
                //if user is correct BUT password is not correct
                etPassword.setTextColor(Color.RED);
            }
        }else{
            Log.d(TAG, "Server checked mail: mail is NOT correct");
            //if user is not correct
            etEmail.setTextColor(Color.RED);
        }
    }

    private void rememberUser(String key1, String key2) {
        sharedPrefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPrefs.edit();
        ed.putString(SendKeys.NICK, key1);
        ed.commit();
        ed.putString(SendKeys.PASS, key2);
        ed.commit();
    }



    private boolean checkEmail(String keyWord){
        if(keyWord.equals("")) return false;

        Map map = new HashMap<String, String>();
        map.put(SendKeys.TITLE, SendKeys.CHECK_MAIL);
        map.put(SendKeys.EMAIL, keyWord);

        LogInThread signThread = new LogInThread(map);
        signThread.start();

        while(signThread.getAnswer()==null); // potential dead loop!!!
        Log.d(TAG, "Server's answer is: " + signThread.getAnswer());

        signThread.close();
        signThread.interrupt();

        Boolean answ = signThread.getAnswer();

        if(!answ){
            etEmail.setTextColor(Color.RED);
            etEmail.setHintTextColor(Color.RED);
        }
        return answ;
    }

    private boolean checkPassword(String email, String pass){
        if(pass.equals("")) return false;

        Map map = new HashMap<String, String>();
        map.put(SendKeys.TITLE, SendKeys.CHECK_PASS);
        map.put(SendKeys.EMAIL, email);
        map.put(SendKeys.PASS, pass);


        LogInThread signThread = new LogInThread(map);
        signThread.start();

        while(signThread.getAnswer()==null); // potential dead loop!!!
        Log.d(TAG, "Server's answer is: " + signThread.getAnswer());

        signThread.close();
        signThread.interrupt();

        Boolean answ = signThread.getAnswer();

        if(!answ){
            etPassword.setTextColor(Color.RED);
            etPassword.setHintTextColor(Color.RED);
        }
        return answ;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null){
            return;
        }
        if(resultCode == RESULT_OK) {
            String nick = data.getStringExtra(SendKeys.EMAIL);
            //Log.d(TAG, SendKeys.NICK + nick);
            etEmail.setText(nick);
            etPassword.setText("");
            tvRegSucc.setText(R.string.regSuccess);
        }
    }
    
    @Override
    public void onBackPressed() {
        //Override to disable back key pressed
    }

    public void setConnectionOFF(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnSignIn.setEnabled(false);
                tvRegister.setEnabled(false);
                tvConnInfo.setVisibility(View.VISIBLE);
            }
        });
    }

    public void setConnectionON() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnSignIn.setEnabled(true);
                tvRegister.setEnabled(true);
                tvConnInfo.setVisibility(View.INVISIBLE);
            }
        });
    }
}