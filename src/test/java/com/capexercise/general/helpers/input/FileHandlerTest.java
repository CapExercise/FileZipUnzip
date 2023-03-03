package com.capexercise.general.helpers.input;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class FileHandlerTest {
    IDataHandle testRef;

    @Before
    public void setup(){
        testRef = new FileHandler("src/main/java/com/capexercise/Files/nonexistent.txt");
    }


    @Test(expected = RuntimeException.class)
    public void testReadContentForNonexistent(){
        String actual = testRef.readContent();
    }
    @Test
    public void testReadContentForEmpty(){
        testRef = new FileHandler("src/main/java/com/capexercise/Files/empty.txt");
        String actual = testRef.readContent();
        String expected = "";
        assertEquals(expected,actual);
    }

    @Test
    public void testReadContent() {
        testRef = new FileHandler("src/main/java/com/capexercise/Files/testFile.txt");
        String actual = testRef.readContent();
        String expected = "Hello world.This is it.";
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