package com.example.calculator;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class calculatorTest extends TestCase {

    @Test
    public void testAdd(){
       calculator cal = new calculator();
        assertEquals("5",cal.add("2","3"));
        assertEquals("0",cal.add("2","-2"));
        assertEquals("0",cal.add("0","0"));
    }
    public void testMultiply() {
        calculator cal = new calculator();
        assertEquals("6",cal.multiply("2","3"));
        assertEquals("0",cal.multiply("0","0"));
        assertEquals("15",cal.multiply("5","3"));
    }

    public void testSubtract() {
        calculator cal = new calculator();
        assertEquals("-1",cal.subtract("2","3"));
        assertEquals("-5",cal.subtract("2","7"));
        assertEquals("0",cal.subtract("0","0"));
    }

    public void testDivide() {
        calculator cal = new calculator();
        assertEquals("2",cal.divide("6","3"));
        assertEquals("-2",cal.divide("-6","3"));
        assertEquals("Error",cal.divide("0","0"));
    }

}