package com.capexercise.huffman.variations.word.compressor;

import com.capexercise.general.helpers.filedata.IFileContents;
import com.capexercise.general.helpers.input.IDataHandle;
import com.capexercise.general.helpers.input.StringHandler;
import com.capexercise.general.helpers.maps.CharMaps;
import com.capexercise.general.helpers.maps.IMap;
import com.capexercise.general.helpers.maps.WordMaps;
import com.capexercise.general.helpers.nodes.CharTreeNode;
import com.capexercise.general.helpers.nodes.StringTreeNode;
import com.capexercise.general.helpers.nodes.TreeNode;
import com.capexercise.huffman.variations.topword.compressor.TopWordCompress;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class WordCompressTest {

    WordCompress testRef=new WordCompress();

    TreeNode root=null;

    @Before
    public void beforeTest()
    {
        TreeNode leftNode=new StringTreeNode("a",6);
        leftNode.setLeft(null);
        leftNode.setRight(null);

        TreeNode rightNode=new StringTreeNode("b",7);
        rightNode.setRight(null);
        rightNode.setLeft(null);

        TreeNode rootNode=new StringTreeNode("-",leftNode.getFrequency()+ rightNode.getFrequency());
        rootNode.setLeft(leftNode);
        rootNode.setRight(rightNode);

        root=rootNode;
    }
    @Test
    public void testFrequencyMapGenerationForPositiveCondn()
    {
        IDataHandle dataRef=new StringHandler("HELLO WORLD.HELLO FROM CAPILLARY.");

        IMap imap=testRef.calculateFreq(dataRef);

        Map<Object,Integer> returnedmap=imap.returnFreqMap();

        Map<Object,Integer> expectedMap=new HashMap<>();

        expectedMap.put(" ",3);
        expectedMap.put("HELLO",2);
        expectedMap.put("FROM",1);
        expectedMap.put("WORLD",1);
        expectedMap.put("CAPILLARY",1);
        expectedMap.put(".",2);
        assertEquals(expectedMap,returnedmap);

    }

    @Test
    public void testfrequencyMapforEmptyCondn()
    {
        IDataHandle dataref=new StringHandler("");
        IMap imap=testRef.calculateFreq(dataref);
        Map<Object,Integer> returnedMap=imap.returnFreqMap();
        assertTrue(returnedMap.size()==0);

    }

    @Test
    public void testfrequencyMapContainingSpecialCharachters()
    {
        IDataHandle dataRef=new StringHandler("HELLO WORLD.HELLO FROM CAPILLARY ™.");

        IMap imap=testRef.calculateFreq(dataRef);
        Map<Object,Integer> returnedMap=imap.returnFreqMap();
        Map<Object,Integer> expectedMap=new HashMap<>();
        expectedMap.put(" ",4);

       expectedMap.put("™",1);
        expectedMap.put("HELLO",2);
        expectedMap.put("FROM",1);
        expectedMap.put("WORLD",1);
        expectedMap.put("CAPILLARY",1);
        expectedMap.put(".",2);
      assertEquals(expectedMap,returnedMap);
    }

    @Test
    public void testiterateTreeForPositiveCondn()
    {
        IMap imap=new WordMaps();
        String ans="";

        testRef.iterateTreeAndCalculateHuffManCode(root,ans,imap);
        Map<Object,String> expectedMap=new HashMap<>();
        Map<Object,String> huffmanMap=imap.returnHuffMap();

        expectedMap.put("a","0");
        expectedMap.put("b","1");

        assertEquals(expectedMap,huffmanMap);
    }

    @Test
    public void TestIterateTreeAndCalcuateHUffManMap_ForSingleNode()
    {
        String ans="";
        TreeNode singleNode =new CharTreeNode('a',2);
        singleNode.setRight(null);
        singleNode.setLeft(null);

        IMap imap=new CharMaps();


        testRef.iterateTreeAndCalculateHuffManCode(singleNode,ans,imap);
        Map<Object,String> huffManMap=imap.returnHuffMap();
        Map<Character,String> expectedHUffManMap=new HashMap<>();
        expectedHUffManMap.put('a',"");
        assertEquals(expectedHUffManMap,huffManMap);
    }

    @Test
    public void testCompressForPositiveCodn() {
        IDataHandle dataRef = new StringHandler("aaaaaabbbbbbb");
        //0000001111111
        IMap map = new WordMaps();
        Map<Object,Integer> freqMap=new HashMap<>();
        freqMap.put("a",6);
        freqMap.put("b",7);
        Map<Object,String> huffMap=new HashMap<>();
        huffMap.put("a","0");
        huffMap.put("b","1");
        map.setFreqMap(freqMap);
        map.setHuffMap(huffMap);

        IFileContents contents=testRef.compress(map, dataRef);
        byte[] bytearr=contents.getByteArray();
        int returnedNoOfZeros=contents.getExtraBits();

        assertArrayEquals(bytearr,new byte[]{-128,0});
        assertEquals(returnedNoOfZeros,7);

    }



    @Test
    public void testCompressForEmptyCodn()
    {
        IDataHandle dataRef = new StringHandler("");

        IMap map = new CharMaps();

        IFileContents contents=testRef.compress(map, dataRef);
        byte[] bytearr=contents.getByteArray();
        int returnedNoOfZeros=contents.getExtraBits();
        assertTrue(bytearr.length==0);
        assertTrue(returnedNoOfZeros==0);

    }

    @Test
    public void testCompressForPositiveCodn1() {
        IDataHandle dataRef = new StringHandler("HELLO WORLD");

        IMap map = new WordMaps();
        Map<Object,Integer> freqMap=new HashMap<>();
        freqMap.put("HELLO",1);
        freqMap.put("WORLD",1);
        Map<Object,String> huffMap=new HashMap<>();
        huffMap.put("HELLO","0");
        huffMap.put("WORLD","1");
        map.setFreqMap(freqMap);
        map.setHuffMap(huffMap);

        IFileContents contents=testRef.compress(map, dataRef);
        byte[] bytearr=contents.getByteArray();
        int returnedNoOfZeros=contents.getExtraBits();

        assertEquals(7,returnedNoOfZeros);
        assertEquals(0,bytearr[0]);
    }









}