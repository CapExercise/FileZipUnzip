package com.capexercise.general.helpers.maps;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class WordMapsTest {

    IMap testRef;

    @Before
    public void setup(){
        testRef = new WordMaps();
    }

    @Test
    public void putFrequency() {

        // words
       for(int i=0;i<3;i++)
        testRef.putFrequency("hello", testRef.getFrequency("hello"));
        for(int i=0;i<2;i++)
            testRef.putFrequency("world", testRef.getFrequency("hello"));
        for(int i=0;i<1;i++)
            testRef.putFrequency("Rohan", testRef.getFrequency("hello"));

        //characters
        for(int i=0;i<3;i++)
            testRef.putFrequency('a', testRef.getFrequency('a'));
        for(int i=0;i<2;i++)
            testRef.putFrequency('b', testRef.getFrequency('b'));
        for(int i=0;i<1;i++)
            testRef.putFrequency('c', testRef.getFrequency('c'));

        Map<Object,Integer> myMap = (Map<Object, Integer>) testRef.returnFreqMap();
        for(Map.Entry<Object,Integer> mapEle:myMap.entrySet()){
            System.out.println(mapEle.getKey()+"\t"+mapEle.getValue());
        }

        assertTrue(true);
    }
}