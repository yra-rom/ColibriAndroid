package com.example.plague.app090816registration.Registration.clients;

import com.example.plague.app090816registration.connection_defaults.Constants.SendKeys;
import com.example.plague.app090816registration.connection_defaults.UserPak.User;
import com.example.plague.app090816registration.connection_defaults.clients.ClientThread;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegistrationThread extends ClientThread{
    private User user;
    private Boolean answer = null;

    public RegistrationThread(User user){
        this.user = user;
    }

    @Override
    protected void write() throws IOException, ClassNotFoundException {
        registerUser();
    }

    private void registerUser() {

        String name = user.getName();
        String email = user.getEmail();
        String pass = user.getPass();

        HashMap map = new HashMap<String, String>();
        map.put(SendKeys.TITLE, SendKeys.REGISTER);
        map.put(SendKeys.NICK, name);
        map.put(SendKeys.EMAIL, email);
        map.put(SendKeys.PASS, pass);



    }

    @Override
    protected void read() throws IOException, ClassNotFoundException {
        answer = (Boolean) input.readObject();
    }

    public Boolean getAnswer() {
        return answer;
    }
}
