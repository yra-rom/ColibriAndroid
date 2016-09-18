package com.example.plague.app090816registration.Registration.clients;

import com.example.plague.app090816registration.connection_defaults.Constants.SendKeys;
import com.example.plague.app090816registration.connection_defaults.UserPak.User;
import com.example.plague.app090816registration.connection_defaults.clients.ClientThread;

import java.util.HashMap;

public class Registration {
    private User user;
    private Boolean answer = null;
    public Boolean getAnswer() {
        return answer;
    }

    public Registration(User user){
        this.user = user;
    }

    public void registerUser(){
        String name = user.getName();
        String email = user.getEmail();
        String pass = user.getPass();

        HashMap mapSend = new HashMap();
        mapSend.put(SendKeys.TITLE, SendKeys.REGISTER);
        mapSend.put(SendKeys.NICK, name);
        mapSend.put(SendKeys.EMAIL, email);
        mapSend.put(SendKeys.PASS, pass);

        ClientThread.getInstance().toSend(mapSend);

        HashMap mapReceive = null;
        while(mapReceive == null) {
            mapReceive = ClientThread.getInstance().getAnswerFor(SendKeys.REGISTER);
        }
        answer = (SendKeys.TRUE).equals(mapReceive.get(SendKeys.ANSWER));
    }

}
