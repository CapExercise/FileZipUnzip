package com.capexercise.general.helpers.input;

import org.junit.Before;
import org.junit.Test;

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
    public void testReadContentAsArrayForEmptyString() {
        testRef = new StringHandler("");
        String[] actual = testRef.readContentAsArray();
        String[] expected = new String[]{};
        assertArrayEquals(expected,actual);
    }

    @Test
    public void testReadContentAsArrayForStringInput() {
        testRef = new StringHandler("hello world");
        String[] actual = testRef.readContentAsArray();
        String[] expected = new String[]{"hello", " ", "world"};
        assertArrayEquals(expected,actual);
    }

    @Test
    public void testReadContentAsArrayForEmptyByteArray() {
        testRef = new StringHandler(new byte[]{});
        String[] actual = testRef.readContentAsArray();
        String[] expected = new String[]{};
        assertEquals(expected, actual);
    }
    @Test
    public void testReadContentAsArrayForByteArray() {
        String str = "hello world";
        byte[] input = str.getBytes();
        testRef = new StringHandler(input);

        String[] actual = testRef.readContentAsArray();
        String[] expected = new String[]{"hello", " ", "world"};

        assertEquals(expected,actual);
    }

}