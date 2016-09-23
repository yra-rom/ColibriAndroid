package com.example.plague.app090816registration.connection_defaults;

public class WhoAmI {
    private static String nick;
    private static String email;

    public WhoAmI(final String n, final String e) {
        nick = n;
        email = e;
    }

    public WhoAmI(String e) {
        email = e;
    }

    public static String getNick() {
        return nick;
    }

    public static String getEmail() {
        return email;
    }
}
