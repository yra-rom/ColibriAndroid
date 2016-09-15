package com.example.plague.app090816registration.LogIn.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.plague.app090816registration.R;
import com.example.plague.app090816registration.Registration.activities.RegistrationActivity;
import com.example.plague.app090816registration.Tabs.TabsActivity;
import com.example.plague.app090816registration.connection_defaults.clients.CheckConnectionThread;
import com.example.plague.app090816registration.connection_defaults.Constants.SendKeys;
import com.example.plague.app090816registration.connection_defaults.chekers.Check;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG = "LogInActivity";

    private EditText etEmail;
    private EditText etPassword;

    private TextView tvRegister;
    private TextView tvRegSucc;
    private TextView tvConnInfo;
    private TextView tvLoginInfo;

    private Button btnSignIn;

    private CheckBox cbKeepLogged;

    private CheckConnectionThread chConn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        initViews();
        startCheckingConnection();
    }

    @Override
    protected void onPause() {
        chConn.interrupt();
        super.onPause();
    }

    @Override
    protected void onRestart() {
        startCheckingConnection();
        super.onRestart();
    }

    private void initViews() {
        etEmail = (EditText) findViewById(R.id.sign_etEmail);
        etPassword = (EditText) findViewById(R.id.sign_etPassword);

        tvRegister = (TextView) findViewById(R.id.tvRegister);
        tvRegSucc = (TextView) findViewById(R.id.sign_tvRegSucc);
        tvConnInfo = (TextView) findViewById(R.id.sign_tvConnectionInfo);
        tvLoginInfo = (TextView) findViewById(R.id.sign_tvLoginInfo);

        btnSignIn = (Button) findViewById(R.id.sign_btnSignIn);

        cbKeepLogged = (CheckBox) findViewById(R.id.sign_cbKeepLogged);

        btnSignIn.setOnClickListener(this);
        tvRegister.setOnClickListener(this);

        addTextChangeListeners();
    }

    private void startCheckingConnection() {
        chConn = new CheckConnectionThread(this);
        chConn.start();
    }

    private void addTextChangeListeners() {
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

        Check ch = Check.getInstance();

        if(ch.checkEmail(email)){
            if(ch.checkPassword(email, pass)){
                if(cbKeepLogged.isChecked()){
                    rememberUser(email, pass);
                }
                rememberMe(email);
                Intent intent = new Intent(this, TabsActivity.class);
                intent.putExtra(SendKeys.EMAIL, email);
                startActivity(intent);
                //TO DO Activity when is logged in
                //TO DO when new activity will start start Receive Threads
                //TO DO when choose person to dialog send new Send Thread
            }else{
                etPassword.setTextColor(Color.RED);
                etPassword.setHintTextColor(Color.RED);
                tvLoginInfo.setText(R.string.wrongPass);
            }
        }else{
            etEmail.setTextColor(Color.RED);
            etEmail.setHintTextColor(Color.RED);
            tvLoginInfo.setText(R.string.wrongEmail);
        }
    }

    private void rememberMe(String email) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(SendKeys.EMAIL, email);
        editor.apply();//???
    }

    private void rememberUser(String email, String pass) {
//        SharedPreferences sharedPrefs = getPreferences(MODE_PRIVATE);
//        SharedPreferences.Editor ed = sharedPrefs.edit();
//        ed.putString(SendKeys.EMAIL, email);
//        ed.putString(SendKeys.PASS, pass);
//        ed.commit();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(SendKeys.EMAIL, email);
        editor.putString(SendKeys.PASS, pass);
        editor.apply();
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