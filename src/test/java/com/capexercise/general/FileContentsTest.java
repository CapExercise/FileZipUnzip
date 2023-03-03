package com.capexercise.general;

import com.capexercise.general.helpers.filedata.FileContents;
import com.capexercise.general.helpers.filedata.IFileContents;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class FileContentsTest {

    IFileContents testRef, mySpy;

    Map<Object, Integer> testFreqMap;
    byte[] testByteArray;
    int testExtraBits;
    String testKey;
    @Before
    public void setup(){

        testRef = new FileContents();
        mySpy = Mockito.spy(FileContents.class);

        testByteArray = new byte[]{98,98,97,97,97,99,99,99};
        testExtraBits = 3;
        testKey = "4g572fdg7x6532svbs22eschbd6ew7cw";
        testFreqMap = new HashMap<>();

        testFreqMap.put("hello",35);
        testFreqMap.put("world", 43);
        testFreqMap.put("the",10);
        testFreqMap.put(" ",7);
    }

    @Test
    public void testSetFrequencyMap() {
        mySpy.setFrequencyMap(testFreqMap);
        Mockito.verify(mySpy,Mockito.times(1)).setFrequencyMap(testFreqMap);
    }

    @Test
    public void testSetExtraBits() {
        mySpy.setExtraBits(testExtraBits);
        Mockito.verify(mySpy,Mockito.times(1)).setExtraBits(testExtraBits);
    }

    @Test
    public void testSetByteArray() {
        mySpy.setByteArray(testByteArray);
        Mockito.verify(mySpy, Mockito.times(1)).setByteArray(testByteArray);
    }

    @Test
    public void testSetMD5Key() {
        mySpy.setMD5Key(testKey);
        Mockito.verify(mySpy,Mockito.times(1)).setMD5Key(testKey);
    }
    @Test
    public void testGetFrequencyMap() {
        testRef.setFrequencyMap(testFreqMap);
        Map<Object, Integer> actual = testRef.getFrequencyMap();
        assertEquals(testFreqMap, actual);
    }

    @Test
    public void testGetExtraBits() {
        testRef.setExtraBits(testExtraBits);
        int actual = testRef.getExtraBits();
        assertEquals(testExtraBits, actual);
    }

    @Test
    public void testGetByteArray() {
        testRef.setByteArray(testByteArray);
        byte[] actual = testRef.getByteArray();
        assertEquals(testByteArray, actual);
    }

    @Test
    public void testGetMD5key() {
        testRef.setMD5Key(testKey);
        String actual = testRef.getMD5key();
        assertEquals(testKey, actual);
    }


}