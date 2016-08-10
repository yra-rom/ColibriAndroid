package com.example.plague.app090816registration.signUpAndRegistration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.plague.app090816registration.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "SignInActivity";

    public static final String NICK = "NICK";
    public static final String PASS = "PASS";

    private EditText etNickOrEmail;
    private EditText etPassword;
    private Button btnSignIn;
    private TextView tvRegister;
    private CheckBox cbKeepLogged;
    private SharedPreferences sharedPrefs;
    private TextView tvRegSucc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        initViews();
    }

    private void initViews() {
        etNickOrEmail = (EditText) findViewById(R.id.sign_etNameOrEMail);
        etPassword = (EditText) findViewById(R.id.sign_etPassword);
        btnSignIn = (Button) findViewById(R.id.sign_btnSignIn);
        tvRegister = (TextView) findViewById(R.id.tvRegister);
        cbKeepLogged = (CheckBox) findViewById(R.id.sign_cbKeepLogged);
        tvRegSucc = (TextView) findViewById(R.id.sign_tvRegSucc);

        btnSignIn.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_btnSignIn:
                String name = etNickOrEmail.getText().toString();
                String pass = etPassword.getText().toString();
                if(checkName(name)){
                    if(checkPassword(pass)){
                        //if user and password are correct and
                        if(cbKeepLogged.isChecked()){
                            //remember users nick and pass
                            rememberUser(name, pass);
                        }
                        //TO DO Activity when is logged in
                    }else{
                        //if user is correct BUT password is not correct
                        etPassword.setTextColor(Color.RED);
                    }
                }else{
                    //if user is not correct
                    etNickOrEmail.setTextColor(Color.RED);
                }
                break;
            case R.id.tvRegister:
                //if user want to create a new account
                Intent intent = new Intent(this, RegistrationActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
    }

    private void rememberUser(String key1, String key2) {
        sharedPrefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPrefs.edit();
        ed.putString(NICK, key1);
        ed.commit();
        ed.putString(PASS, key2);
        ed.commit();
    }

    private boolean checkName(final String key) {
        //TO DO
        //connect to server and
        //check on database
        return false;
    }

    private boolean checkPassword(final String key) {
        //TO DO
        //connect to server and
        //check on database
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null){
            return;
        }
        if(resultCode == RESULT_OK) {
            String nick = data.getStringExtra("NICK");
            Log.d(TAG, "NICK" + nick);
            etNickOrEmail.setText(nick);
            tvRegSucc.setText(R.string.regSuccess);
        }
    }
    
    @Override
    public void onBackPressed() {
        //Override to disable back key pressed
    }
}