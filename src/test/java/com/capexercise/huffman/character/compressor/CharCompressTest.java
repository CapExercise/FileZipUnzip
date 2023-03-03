package com.capexercise.huffman.character.compressor;

import com.capexercise.general.helpers.filedata.IFileContents;
import com.capexercise.general.helpers.input.IDataHandle;
import com.capexercise.general.helpers.input.StringHandler;
import com.capexercise.general.helpers.maps.CharMaps;
import com.capexercise.general.helpers.maps.IMap;
import com.capexercise.general.helpers.nodes.CharTreeNode;
import com.capexercise.general.helpers.nodes.TreeNode;
import com.capexercise.huffman.variations.character.compressor.CharCompress;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CharCompressTest {


    CharCompress c = new CharCompress();
    TreeNode root = null;

    IMap imap = new CharMaps();


    @Before
    public void beforeTest() {
        TreeNode leftNode = new CharTreeNode('a', 6);
        leftNode.setLeft(null);
        leftNode.setRight(null);

        TreeNode rightNode = new CharTreeNode('b', 7);
        rightNode.setRight(null);
        rightNode.setLeft(null);

        TreeNode rootNode = new CharTreeNode('-', leftNode.getFrequency() + rightNode.getFrequency());
        rootNode.setLeft(leftNode);
        rootNode.setRight(rightNode);

        root = rootNode;
    }

    //aabba
    @Test
    public void testCompressMethodforPositiveCondition() {
        IDataHandle idata = new StringHandler("aabba");

        Map<Object, Integer> frequencyMap = new HashMap<>();
        frequencyMap.put('a', 3);
        frequencyMap.put('b', 2);
        imap.setFreqMap(frequencyMap);

        Map<Object, String> hashMap = new HashMap<>();
        hashMap.put('a', "0");
        hashMap.put('b', "1");
        imap.setHuffMap(hashMap);
        //00110
        IFileContents contents = c.compress(imap, idata);
        assertEquals(frequencyMap, contents.getFrequencyMap());
//       assertEquals(3,contents.getExtraBits());
        assertEquals(48, contents.getByteArray()[0]);
    }

    @Test
    public void testCompressMethodforEmptyCondition() {
        IDataHandle idata = new StringHandler("");
        IMap mp = new CharMaps();
        mp.setHuffMap(new HashMap<>());
        mp.setFreqMap(new HashMap<>());
        IFileContents contents = c.compress(mp, idata);
        assertEquals(0, contents.getByteArray().length);
        assertEquals(0, contents.getExtraBits());

    }

    @Test
    public void testFrequencyMapForPosistiveCondition() {
        IDataHandle idata = new StringHandler("zzaaabbcd");
        IMap imap = c.calculateFreq(idata);
        Map<Object, Integer> expectedMap = new HashMap<>();
        expectedMap.put('a', 3);
        expectedMap.put('b', 2);
        expectedMap.put('c', 1);
        expectedMap.put('d', 1);
        expectedMap.put('z', 2);
        Map<Object, Integer> returnedMap = (Map<Object, Integer>) imap.returnFreqMap();
        assertEquals(expectedMap, returnedMap);
    }

    @Test
    public void testFrequencyMapForSpecialCharacters() {
        IDataHandle iFile = new StringHandler("eerg™");
        IMap imap = c.calculateFreq(iFile);
        Map<Object, Integer> returnedMap = (Map<Object, Integer>) imap.returnFreqMap();
        Map<Object, Integer> expectedMap = new HashMap<>();
        expectedMap.put('™', 1);
        expectedMap.put('e', 2);
        expectedMap.put('r', 1);
        expectedMap.put('g', 1);
        assertEquals(expectedMap, returnedMap);
    }


    @Test
    public void testFrequencyMapForEmptyCondition() {
        IDataHandle iFile = new StringHandler("");
        IMap imap = c.calculateFreq(iFile);
        Map<Object, Integer> returnedMap = (Map<Object, Integer>) imap.returnFreqMap();
        assertTrue(returnedMap.isEmpty());
    }


    @Test
    public void TestIterateTreeAndCalcuateHuffManMap_ForPositiveCase() {
        IMap imap = new CharMaps();
        String ans = "";

        c.iterateTreeAndCalculateHuffManCode(root, ans, imap);
        Map<Object, String> expectedMap = new HashMap<>();
        Map<Object, String> huffmanMap = imap.returnHuffMap();
        expectedMap.put('a', "0");
        expectedMap.put('b', "1");
        assertEquals(expectedMap, huffmanMap);
    }

    @Test
    public void TestIterateTreeAndCalculateHuffManMap_ForNullEmpty() {
        String ans = "";
        TreeNode root = null;
        IMap imap = new CharMaps();
        c.iterateTreeAndCalculateHuffManCode(root, ans, imap);
        Map<Object, String> HuffmanMap = imap.returnHuffMap();
        assertTrue(HuffmanMap.isEmpty());
    }

    @Test
    public void TestIterateTreeAndCalcuateHUffManMap_ForSingleNode() {
        String ans = "";
        TreeNode singleNode = new CharTreeNode('a', 2);
        singleNode.setRight(null);
        singleNode.setLeft(null);

        IMap imap = new CharMaps();


        c.iterateTreeAndCalculateHuffManCode(singleNode, ans, imap);
        Map<Object, String> huffManMap = imap.returnHuffMap();
        Map<Character, String> expectedHUffManMap = new HashMap<>();
        expectedHUffManMap.put('a', "");
        assertEquals(expectedHUffManMap, huffManMap);
    }

    @Test
    public void testCompressForPositiveCodn() {
        IDataHandle dataRef = new StringHandler("aaaaaabbbbbbb");
        //0000001111111
        IMap map = new CharMaps();
        map.putFrequency('a', 5);
        map.putFrequency('b', 6);
        map.putHuffManCode('a', "0");
        map.putHuffManCode('b', "1");

        IFileContents contents=c.compress(map, dataRef);
        byte[] bytearr=contents.getByteArray();
        int returnedNoOfZeros=contents.getExtraBits();


        assertArrayEquals(bytearr,new byte[]{3,-8});
        assertEquals(returnedNoOfZeros,3);

    }



    @Test
    public void testCompressForEmptyCodn()
    {
        IDataHandle dataRef = new StringHandler("");

        IMap map = new CharMaps();

        IFileContents contents=c.compress(map, dataRef);
        byte[] bytearr=contents.getByteArray();
        int returnedNoOfZeros=contents.getExtraBits();
        assertTrue(bytearr.length==0);
        assertTrue(returnedNoOfZeros==0);

    }





}