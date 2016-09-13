package com.example.plague.app090816registration.connection_defaults.UserPak;

public class User {
    private String name;
    private String email;
    private String pass;

    User(UserBuilder builder){
        this.name = builder.getName();
        this.email = builder.getEmail();
        this.pass = builder.getPass();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }
}
