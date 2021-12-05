package com.example.fitnesscentrebooking;

import junit.framework.TestCase;

public class MemberTest extends TestCase {

    public void testGetEmail() {
        Member newUser = new Member("John Smith", "john.smith@uottawa.ca", "52", "25", "member");
        assertEquals(newUser.getEmail(), "john.smith@uottawa.ca");
    }

    public void testSetEmail() {
        Member newUser = new Member("John Smith", "john.smith@uottawa.ca", "52", "25", "member");
        newUser.setEmail("new.email@uottawa.ca");
        assertEquals(newUser.getEmail(), "new.email@uottawa.ca");
    }

    public void testGetUsername() {
        Member newUser = new Member("John Smith", "john.smith@uottawa.ca", "52", "25", "member");
        assertEquals(newUser.getUsername(), "John Smith");
    }
}
