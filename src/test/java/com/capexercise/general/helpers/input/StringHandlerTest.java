package com.capexercise.general.helpers.input;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class StringHandlerTest {
    IDataHandle testRef;

    @Before
    public void setup(){
        testRef = new StringHandler("");
    }
    @Test
    public void testReadContentForEmptyString() {
        String actual = testRef.readContent();
        String expected = "";
        assertEquals(expected,actual);
    }

    @Test
    public void testReadContentForStringInput() {
        testRef = new StringHandler("abbabababbbababa");
        String actual = testRef.readContent();
        String expected = "abbabababbbababa";
        assertEquals(expected,actual);
    }

    @Test
    public void testReadContentForEmptyByteArray() {
        testRef = new StringHandler(new byte[]{});
        String actual = testRef.readContent();
        String expected = "";
        assertEquals(expected, actual);
    }
    @Test
    public void testReadContentForByteArray() {
        String str = "hello world";
        byte[] input = str.getBytes();
        testRef = new StringHandler(input);

        String actual = testRef.readContent();
        String expected = "hello world";

        assertEquals(expected,actual);
    }

    @Test
    public void testSetPercentage(){
//        testRef.setPercentage(20);
        IDataHandle mySpy = Mockito.spy(testRef);
        mySpy.setPercentage(20);
        Mockito.verify(mySpy,Mockito.times(1)).setPercentage(20);
    }

    @Test
    public void testGetPercentage(){
        testRef.setPercentage(20);
        int actual = testRef.getPercentage();
        int expected = 20;
        assertEquals(expected, actual);
    }

}