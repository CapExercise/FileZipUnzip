package com.capexercise.huffman.variations.word.compressor;

import com.capexercise.general.helpers.input.IDataHandle;
import com.capexercise.general.helpers.input.StringHandler;
import com.capexercise.general.helpers.maps.IMap;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class WordCompressTest {

    WordCompress compress=new WordCompress();

    @Test
    public void testfrequencyMapforPositiveCodn()
    {
        IDataHandle data=new StringHandler("HELLO WORLD");
        IMap imap=compress.calculateFreq(data);
        Map<Object,Integer> frequencyMap=imap.returnFreqMap();
        Map<Object,Integer> expectedMap=new HashMap<>();
       for(Map.Entry<Object,Integer> entry: frequencyMap.entrySet())
       {
           System.out.println(entry.getKey()+"  "+entry.getValue());
       }
    }





}