package com.example.fitnesscentrebooking;

import junit.framework.TestCase;

public class UserTest extends TestCase {

    public void testGetEmail() {
        User newUser = new User("John Smith", "john.smith@uottawa.ca", "52", "25", "member");
        assertEquals(newUser.getEmail(), "john.smith@uottawa.ca");
    }

    public void testSetEmail() {
        User newUser = new User("John Smith", "john.smith@uottawa.ca", "52", "25", "member");
        newUser.setEmail("new.email@uottawa.ca");
        assertEquals(newUser.getEmail(), "new.email@uottawa.ca");
    }

    public void testGetUsername() {
        User newUser = new User("John Smith", "john.smith@uottawa.ca", "52", "25", "member");
        assertEquals(newUser.getUsername(), "John Smith");
    }

    public void testSetUsername() {
        User newUser = new User("John Smith", "john.smith@uottawa.ca", "52", "25", "member");
        newUser.setUsername("New Member");
        assertEquals(newUser.getUsername(), "New Member");
    }
}