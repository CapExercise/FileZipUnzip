package com.capexercise.general.helpers.maps;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class WordMapsTest {

    IMap testRef;
    Map<Object, Integer> testFreq;

    Map<Object, String> testHuff;

    @Before
    public void setup(){
        testRef = new WordMaps();

        testFreq = new HashMap<>();
        testHuff = new HashMap<>();

        testFreq.put("hello",2);
        testFreq.put("world",3);

        testHuff.put("hello","0010");
        testHuff.put("world","101");
    }

    @Test
    public void testPutFrequency() {
        IMap mySpy = Mockito.spy(WordMaps.class);
        mySpy.putFrequency("hello",1);
        Mockito.verify(mySpy,Mockito.times(1)).putFrequency("hello",1);
    }

    @Test
    public void testGetFrequency() {
        testRef.putFrequency("hello",2);
        int actual = testRef.getFrequency("hello");
        int expected = 2;
        assertEquals(expected, actual);
    }

    @Test
    public void testPutHuffManCode() {
        IMap mySpy = Mockito.spy(WordMaps.class);
        mySpy.putFrequency("hello",1);
        Mockito.verify(mySpy,Mockito.times(1)).putFrequency("hello",1);
    }

    @Test
    public void testGetHuffmanCode() {
        testRef.putHuffManCode("hello","0010");
        String actual = testRef.getHuffmanCode("hello");
        String expected = "0010";
        assertEquals(expected, actual);
    }

    @Test
    public void testReturnFreqMap() {

        testRef.putFrequency("hello",2);
        testRef.putFrequency("world",3);

        Map<Object, Integer> actual = testRef.returnFreqMap();

        assertEquals(testFreq, actual);

    }

    @Test
    public void testReturnHuffMap() {
        testRef.putHuffManCode("hello","0010");
        testRef.putHuffManCode("world","101");

        Map<Object, String> actual = testRef.returnHuffMap();

        assertEquals(testHuff, actual);
    }

    @Test
    public void testFreqSize() {
        testRef.putFrequency("hello",2);
        testRef.putFrequency("world",3);

        int actual = testRef.freqSize();
        int expected = 2;
        assertEquals(expected, actual);
    }

    @Test
    public void testHuffmanSize() {
        testRef.putHuffManCode("hello","0010");
        testRef.putHuffManCode("world","101");

        int actual = testRef.huffmanSize();
        int expected = 2;
        assertEquals(expected, actual);
    }

    @Test
    public void testContainsFreqKeyPositive() {
        testRef.putFrequency("hello",2);
        boolean val = testRef.containsFreqKey("hello");
        assertTrue(val);
    }

    @Test
    public void testContainsFreqKeyNegative() {
        boolean val = testRef.containsFreqKey('c');
        assertFalse(val);
    }

    @Test
    public void testContainsHuffKeyPositive() {
        testRef.putHuffManCode("hello","0010");
        boolean val = testRef.containsHuffKey("hello");
        assertTrue(val);
    }

    @Test
    public void testContainsHuffKeyNegative(){
        boolean val = testRef.containsHuffKey('c');
        assertFalse(val);
    }

    @Test
    public void testSetFreqMap() {
        IMap mySpy = Mockito.spy(WordMaps.class);
        mySpy.setFreqMap(testFreq);
        Mockito.verify(mySpy, Mockito.times(1)).setFreqMap(testFreq);
    }

    @Test
    public void testSetHuffMap() {
        IMap mySpy = Mockito.spy(WordMaps.class);
        mySpy.setHuffMap(testHuff);
        Mockito.verify(mySpy, Mockito.times(1)).setHuffMap(testHuff);
    }

    @Test
    public void testClearFreqMap() {
        testRef.clearFreqMap();
        int size = testRef.freqSize();
        assertTrue(size == 0);
    }

    @Test
    public void testClearHuffMap() {
        testRef.clearHuffMap();
        int size = testRef.huffmanSize();
        assertTrue(size == 0);
    }

}