package com.example.plague.app090816registration.signUpAndRegistration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{

    public static final String NICK = "NICK";
    public static final String PASS = "PASS";
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPrefs = getPreferences(MODE_PRIVATE);

        if(sharedPrefs.getString(NICK,"") !=null && sharedPrefs.getString(PASS,"") != null){
            Intent intent = new Intent(this, SignInActivity.class);
            startActivityForResult(intent, RESULT_OK);
        }else{
            //TO DO
            //if already logged in
        }
    }

}