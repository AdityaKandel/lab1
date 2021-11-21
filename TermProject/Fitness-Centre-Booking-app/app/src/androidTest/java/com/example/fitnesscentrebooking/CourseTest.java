package com.example.fitnesscentrebooking;

import junit.framework.TestCase;

public class CourseTest extends TestCase {

    public void testGetUserName() {
        Course newCourse = new Course("Basketball", "11/21/2021", "2:30", "3", 25, "user name", "50");
        assertEquals(newCourse.getUserName(), "user name");
    }

    public void testSetUserName() {
        Course newCourse = new Course("Basketball", "11/21/2021", "2:30", "3", 25, "user name", "50");
        newCourse.setUserName("meow");
        assertEquals(newCourse.getUserName(), "meow");
    }

    public void testGetDate() {
        Course newCourse = new Course("Basketball", "11/21/2021", "2:30", "3", 25, "user name", "50");
        assertEquals(newCourse.getDate(), "11/21/2021");
    }

    public void testSetDate() {
        Course newCourse = new Course("Basketball", "11/21/2021", "2:30", "3", 25, "user name", "50");
        newCourse.setDate("12/26/2021");
        assertEquals(newCourse.getDate(), "12/26/2021");
    }

    public void testTestGetName() {
        Course newCourse = new Course("Basketball", "11/21/2021", "2:30", "3", 25, "user name", "50");
        assertEquals(newCourse.getName(), "Basketball");
    }

    public void testTestSetName() {
        Course newCourse = new Course("Basketball", "11/21/2021", "2:30", "3", 25, "user name", "50");
        newCourse.setName("baloncesto");
        assertEquals(newCourse.getName(), "baloncesto");
    }
}