package com.example.plague.app090816registration.Registration.clients;

import com.example.plague.app090816registration.connection_defaults.Constants.SendKeys;
import com.example.plague.app090816registration.connection_defaults.UserPak.User;
import com.example.plague.app090816registration.connection_defaults.clients.ClientThread;

import java.io.IOException;
import java.util.HashMap;

public class RegistrationThread extends ClientThread{
    private User user;
    private Boolean answer = null;
    public Boolean getAnswer() {
        return answer;
    }

    public RegistrationThread(User user){
        this.user = user;
    }

    private void registerUser() throws IOException {
        String name = user.getName();
        String email = user.getEmail();
        String pass = user.getPass();

        HashMap map = new HashMap();
        map.put(SendKeys.TITLE, SendKeys.REGISTER);
        map.put(SendKeys.NICK, name);
        map.put(SendKeys.EMAIL, email);
        map.put(SendKeys.PASS, pass);

        output.flush();
        output.writeObject(map);
        output.flush();
    }

    @Override
    protected void read() throws IOException, ClassNotFoundException {
        answer = (Boolean) input.readObject();
    }

    @Override
    protected void write() throws IOException, ClassNotFoundException {
        registerUser();
    }
}
