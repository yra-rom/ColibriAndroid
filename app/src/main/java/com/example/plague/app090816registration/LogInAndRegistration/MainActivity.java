package com.example.plague.app090816registration.LogInAndRegistration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.plague.app090816registration.clients.SendKeys;

public class MainActivity extends AppCompatActivity{
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPrefs = getPreferences(MODE_PRIVATE);
        String s1 = sharedPrefs.getString(SendKeys.NICK,"");
        String s2 = sharedPrefs.getString(SendKeys.PASS,"");
        if(s1.equals("") || s2.equals("")){
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
        }else {
            //TO DO
            //if already logged in
        }
    }

}