package com.example.shuttlemobile.admin;

import com.example.shuttlemobile.user.User;

/**
 * Used only for polymorphism.
 */
public class Admin extends User {
    public Admin() {
        super("Admin", "", "", "", "", "", "", false, true);
    }
}
