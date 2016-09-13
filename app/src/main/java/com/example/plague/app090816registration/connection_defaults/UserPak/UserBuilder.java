package com.example.plague.app090816registration.connection_defaults.UserPak;

public class UserBuilder {
    private String name;
    private String email;
    private String pass;

    public UserBuilder name(String name){
        this.name = name;
        return this;
    }

    public UserBuilder email(String email){
        this.email = email;
        return this;
    }

    public UserBuilder pass(String pass){
        this.pass = pass;
        return this;
    }

    public User build(){
        return new User(this);
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
