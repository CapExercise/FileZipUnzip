package com.capexercise.huffman.variations.topword.compressor;

import com.capexercise.general.helpers.input.IDataHandle;
import com.capexercise.general.helpers.input.StringHandler;
import com.capexercise.general.helpers.maps.CharMaps;
import com.capexercise.general.helpers.maps.IMap;
import com.capexercise.general.helpers.maps.WordMaps;
import com.capexercise.general.helpers.nodes.CharTreeNode;
import com.capexercise.general.helpers.nodes.TreeNode;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class TopWordCompressTest {

    TopWordCompress testRef=new TopWordCompress();

    TreeNode root=null;

    @Before
    public void beforeTest()
    {
        TreeNode leftNode=new CharTreeNode('a',6);
        leftNode.setLeft(null);
        leftNode.setRight(null);

        TreeNode rightNode=new CharTreeNode('b',7);
        rightNode.setRight(null);
        rightNode.setLeft(null);

        TreeNode rootNode=new CharTreeNode('-',leftNode.getFrequency()+ rightNode.getFrequency());
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
        expectedMap.put("A",2);
        expectedMap.put("C",1);
        expectedMap.put("D",1);
        expectedMap.put("HELLO",2);
        expectedMap.put("F",1);
        expectedMap.put("I",1);
        expectedMap.put("L",3);
        expectedMap.put("M",1);
        expectedMap.put(".",2);
        expectedMap.put("O",2);
        expectedMap.put("P",1);
        expectedMap.put("R",3);
        expectedMap.put("W",1);
        expectedMap.put("Y",1);
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
        expectedMap.put("A",2);
        expectedMap.put("C",1);
        expectedMap.put("D",1);
        expectedMap.put("HELLO",2);
        expectedMap.put("F",1);
        expectedMap.put("I",1);
        expectedMap.put("L",3);
        expectedMap.put("M",1);
        expectedMap.put(".",2);
        expectedMap.put("O",2);
        expectedMap.put("P",1);
        expectedMap.put("R",3);
        expectedMap.put("W",1);
        expectedMap.put("Y",1);
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
        expectedMap.put('a',"0");
        expectedMap.put('b',"1");
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




}