package com.example.fitnesscentrebooking;

import java.util.ArrayList;
import junit.framework.TestCase;
public class EnrollClasstTest extends TestCase {


    public void testValidateTime() {

        assertEquals(false,scheduleList.ValidateTime("2:30","4:50","3:00","5:00") );
      assertEquals(true,scheduleList.ValidateTime("2:30","4:50","5:00","6:00") );
        assertEquals(scheduleList.ValidateTime("2:30","4:50","1:00","1:45"),true );
    }

    public void testCapacity(){
        assertEquals(true, scheduleList.CheckCapacityLimit(new Course("","","","0","",4,"","",2)));
        assertEquals(false, scheduleList.CheckCapacityLimit(new Course("","","","0","",4,"","",4)));
        assertEquals(true, scheduleList.CheckCapacityLimit(new Course("","","","0","",4,"","",3)));

    }
    public void testIncrement(){
        assertEquals(3, scheduleList.incrementEnrolled(new Course("","","","0","",4,"","",2)));
        assertEquals(5, scheduleList.incrementEnrolled(new Course("","","","0","",4,"","",4)));
        assertEquals(4, scheduleList.incrementEnrolled(new Course("","","","0","",4,"","",3)));

    }
    public void testDecrement(){
        assertEquals(1, scheduleList.decrementEnrolled(new Course("","","","0","",4,"","",2)));
        assertEquals(3, scheduleList.decrementEnrolled(new Course("","","","0","",4,"","",4)));
        assertEquals(2, scheduleList.decrementEnrolled(new Course("","","","0","",4,"","",3)));

    }
}
