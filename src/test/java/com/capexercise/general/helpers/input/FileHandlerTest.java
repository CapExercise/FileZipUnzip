package com.capexercise.general.helpers.input;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileHandlerTest {
    IDataHandle testRef;

    @Before
    public void setup(){
        testRef = new FileHandler("src/main/java/com/capexercise/Files/empty.txt");
    }

    @Test
    public void testReadContentForEmpty(){
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
    public void testReadContentAsArrayForEmpty(){
        testRef = new FileHandler("src/main/java/com/capexercise/Files/empty.txt");
        String[] actual = testRef.readContentAsArray();
        String[] expected = new String[0];
        assertArrayEquals(expected,actual);
    }
    @Test
    public void testReadContentAsArray(){
        testRef = new FileHandler("src/main/java/com/capexercise/Files/testFile.txt");
        String[] actual = testRef.readContentAsArray();
        String[] expected = new String[]{"Hello"," ","world",".","This"," ","is"," ","it","."};
        assertArrayEquals(expected,actual);
    }
}