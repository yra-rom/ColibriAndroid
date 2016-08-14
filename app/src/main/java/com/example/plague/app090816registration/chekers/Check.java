package com.example.plague.app090816registration.chekers;

import android.util.Log;

import com.example.plague.app090816registration.clients.LogInThread;
import com.example.plague.app090816registration.clients.SendKeys;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Check {
    public static final String TAG= "Check";

    private static Check instance = new Check();
    public static Check getInstance() {
        return instance;
    }

    private Check(){
    }

    public boolean checkEmailLocal(String email){
        int l = email.length();
        if(l < 3 || l > 32){
            return false;
        }

        //checking with regex
        Pattern p = Pattern.compile(".+@.+");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public boolean checkEmail(String email){
        return checkEmailLocal(email) && checkEmailDB(email);
    }

    public boolean checkEmailDB(String email) {
        Map map = new HashMap<String, String>();
        map.put(SendKeys.TITLE, SendKeys.CHECK_MAIL);
        map.put(SendKeys.EMAIL, email);

        LogInThread signThread = new LogInThread(map);
        signThread.start();

        while(signThread.getAnswer()==null); // potential dead loop!!!
        Log.d(TAG, "Server's answer is: " + signThread.getAnswer());

        signThread.close();
        signThread.interrupt();

        return signThread.getAnswer();
    }

    public boolean checkPassword(String email, String pass){
        return checkPasswordLocal(pass) && checkPasswordDB(email,pass);
    }

    public boolean checkPasswordDB(String email, String pass) {
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

        return signThread.getAnswer();
    }

    public boolean checkPasswordLocal(String pass) {
        int l = pass.length();
        return l > 4 && l < 17;
    }

    public boolean checkEmailIsFree(String email) {
        Map map = new HashMap<String, String>();
        map.put(SendKeys.TITLE, SendKeys.CHECK_MAIL);
        map.put(SendKeys.EMAIL, email);

        LogInThread signThread = new LogInThread(map);
        signThread.start();
        while(signThread.getAnswer()==null); // potential dead loop!!!
        Log.d(TAG, "Server's answer is: " + signThread.getAnswer());

        signThread.close();
        signThread.interrupt();

        return !signThread.getAnswer();
    }

}
