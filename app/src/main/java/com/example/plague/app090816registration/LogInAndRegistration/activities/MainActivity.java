package com.example.plague.app090816registration.LogInAndRegistration.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.example.plague.app090816registration.connection_defaults.SendKeys;

public class MainActivity extends AppCompatActivity{
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Where everything begins
//        SharedPreferences sharedPrefs = getPreferences(MODE_PRIVATE);
//        String s1 = sharedPrefs.getString(SendKeys.EMAIL, "");
//        String s2 = sharedPrefs.getString(SendKeys.PASS, "");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String s1 = preferences.getString(SendKeys.EMAIL, "");
        String s2 = preferences.getString(SendKeys.PASS, "");

        if(s1.equals("") || s2.equals("")){
            Intent intent = new Intent(this, LogInActivity.class);
            intent.putExtras(this.getIntent());
            startActivity(intent);
        }else {
            //TO DO
            //if already logged in
        }
    }

}