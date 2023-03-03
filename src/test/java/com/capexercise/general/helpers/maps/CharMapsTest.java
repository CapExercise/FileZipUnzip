package com.capexercise.general.helpers.maps;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CharMapsTest {

    IMap testRef;
    Map<Object, Integer> testFreq;

    Map<Object, String> testHuff;

    @Before
    public void setup(){
        testRef = new CharMaps();

        testFreq = new HashMap<>();
        testHuff = new HashMap<>();

        testFreq.put('a',2);
        testFreq.put('b',3);

        testHuff.put('a',"0010");
        testHuff.put('b',"101");
    }

    @Test
    public void testPutFrequency() {
        IMap mySpy = Mockito.spy(CharMaps.class);
        mySpy.putFrequency('a',1);
        Mockito.verify(mySpy,Mockito.times(1)).putFrequency('a',1);
    }

    @Test
    public void testGetFrequency() {
        testRef.putFrequency('a',2);
        int actual = testRef.getFrequency('a');
        int expected = 2;
        assertEquals(expected, actual);
    }

    @Test
    public void testPutHuffManCode() {
        IMap mySpy = Mockito.spy(CharMaps.class);
        mySpy.putFrequency('a',1);
        Mockito.verify(mySpy,Mockito.times(1)).putFrequency('a',1);
    }

    @Test
    public void testGetHuffmanCode() {
        testRef.putHuffManCode('a',"0010");
        String actual = testRef.getHuffmanCode('a');
        String expected = "0010";
        assertEquals(expected, actual);
    }

    @Test
    public void testReturnFreqMap() {

        testRef.putFrequency('a',2);
        testRef.putFrequency('b',3);

        Map<Object, Integer> actual = testRef.returnFreqMap();

        assertEquals(testFreq, actual);

    }

    @Test
    public void testReturnHuffMap() {
        testRef.putHuffManCode('a',"0010");
        testRef.putHuffManCode('b',"101");

        Map<Object, String> actual = testRef.returnHuffMap();

        assertEquals(testHuff, actual);
    }

    @Test
    public void testFreqSize() {
        testRef.putFrequency('a',2);
        testRef.putFrequency('b',3);

        int actual = testRef.freqSize();
        int expected = 2;
        assertEquals(expected, actual);
    }

    @Test
    public void testHuffmanSize() {
        testRef.putHuffManCode('a',"0010");
        testRef.putHuffManCode('b',"101");

        int actual = testRef.huffmanSize();
        int expected = 2;
        assertEquals(expected, actual);
    }

    @Test
    public void testContainsFreqKeyPositive() {
        testRef.putFrequency('a',2);
        boolean val = testRef.containsFreqKey('a');
        assertTrue(val);
    }

    @Test
    public void testContainsFreqKeyNegative() {
        boolean val = testRef.containsFreqKey('c');
        assertFalse(val);
    }

    @Test
    public void testContainsHuffKeyPositive() {
        testRef.putHuffManCode('a',"0010");
        boolean val = testRef.containsHuffKey('a');
        assertTrue(val);
    }

    @Test
    public void testContainsHuffKeyNegative(){
        boolean val = testRef.containsHuffKey('c');
        assertFalse(val);
    }

    @Test
    public void testSetFreqMap() {
        IMap mySpy = Mockito.spy(CharMaps.class);
        mySpy.setFreqMap(testFreq);
        Mockito.verify(mySpy, Mockito.times(1)).setFreqMap(testFreq);
    }

    @Test
    public void testSettHuffMap() {
        IMap mySpy = Mockito.spy(CharMaps.class);
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
        testRef.clearFreqMap();
        int size = testRef.freqSize();
        assertTrue(size == 0);
    }
}