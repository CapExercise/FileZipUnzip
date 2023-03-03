package com.capexercise.huffman.topword.compressor;

import com.capexercise.general.helpers.input.IDataHandle;
import com.capexercise.general.helpers.input.StringHandler;
import com.capexercise.general.helpers.maps.CharMaps;
import com.capexercise.general.helpers.maps.IMap;
import com.capexercise.general.helpers.maps.WordMaps;
import com.capexercise.general.helpers.nodes.StringTreeNode;
import com.capexercise.general.helpers.nodes.TreeNode;
import com.capexercise.huffman.variations.topword.compressor.TopWordCompress;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class TopWordCompressTest {
/*
    TopWordCompress c=new TopWordCompress();
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
    public void testFrequencyMapForPositiveCondition()
    {

        IDataHandle idata=new StringHandler("Hello world this is new world.Hello How are you?");
        IMap imap=c.calculateFreq(idata);
        Map<Object,Integer> expectedMap=new HashMap<>();
        expectedMap.put(" ",8);
        expectedMap.put("a",1);
        expectedMap.put("d",2);
        expectedMap.put("e",2);
        expectedMap.put("h",1);
        expectedMap.put("H",1);
        expectedMap.put("i",2);
        expectedMap.put("l",2);
        expectedMap.put("n",1);
        expectedMap.put(".",1);
        expectedMap.put("o",4);
        expectedMap.put("r",3);
        expectedMap.put("s",2);
        expectedMap.put("Hello",2);
        expectedMap.put("t",1);
        expectedMap.put("u",1);
        expectedMap.put("w",4);
        expectedMap.put("y",1);
        expectedMap.put("?",1);

        Map<Object,Integer> returnedMap= imap.returnFreqMap();
//        for(Map.Entry<Object,Integer> entry:returnedMap.entrySet())
//        {
//            System.out.println(entry.getKey()+"   "+entry.getValue());
//        }
        assertEquals(expectedMap,returnedMap);
    }


    @Test
    public void testFrequencyMapForEmptyCondition()
    {
        IDataHandle iFile=new StringHandler("");
        IMap imap= c.calculateFreq(iFile);
        Map<Object, Integer> returnedMap= imap.returnFreqMap();
        assertTrue(returnedMap.isEmpty());
    }

    @Test
    public void testFrequencyMapForSpecialCharacters()
    {
        IDataHandle iFile=new StringHandler("Hello world this is new world.Hello How are you? eerg™");
        IMap imap= c.calculateFreq(iFile);
        Map<Object,Integer> returnedMap= imap.returnFreqMap();
        Map<Object,Integer> expectedMap=new HashMap<>();
         expectedMap.put(" ",9);
        expectedMap.put("a",1);
        expectedMap.put("d",2);
        expectedMap.put("e",4);
        expectedMap.put("h",1);
        expectedMap.put("g",1);
        expectedMap.put("H",1);
        expectedMap.put("i",2);
        expectedMap.put("l",2);
        expectedMap.put("n",1);
        expectedMap.put(".",1);
        expectedMap.put("o",4);
        expectedMap.put("r",4);
        expectedMap.put("s",2);
        expectedMap.put("Hello",2);
        expectedMap.put("t",1);
        expectedMap.put("u",1);
        expectedMap.put("w",4);
        expectedMap.put("y",1);
        expectedMap.put("?",1);
        expectedMap.put("™",1);

        assertEquals(expectedMap,returnedMap);
    }








    @Test
    public void TestZerosToBeappended()
    {
        StringBuilder str=new StringBuilder("00010");
        assertEquals(3,c.noofZerosToBeAppended(str));
    }
    @Test
    public void TestZerosToBeappended2()
    {
        StringBuilder str=new StringBuilder("");
        assertEquals(0,c.noofZerosToBeAppended(str));
    }
    @Test
    public void TestZerosToBeappended3()
    {
        StringBuilder str=new StringBuilder("0001");
        assertEquals(4,c.noofZerosToBeAppended(str));
    }
    @Test
    public void TestZerosToBeappended4()
    {
        StringBuilder str=new StringBuilder("0001000");
        assertEquals(1,c.noofZerosToBeAppended(str));
    }
    @Test
    public void TestZerosToBeappended5()
    {
        StringBuilder str=new StringBuilder("00010000");
        assertEquals(0,c.noofZerosToBeAppended(str));
    }

    @Test
    public void TestappendRemainingZeros2()
    {
        StringBuilder inputString=new StringBuilder("00001");
        StringBuilder returnedString=c.appendRemainingZeros(inputString);
        assertEquals("00001000",returnedString.toString());
    }

    @Test
    public void TestappendRemainingZeros3()
    {
        StringBuilder inputString=new StringBuilder("10101100");
        StringBuilder returnedString=c.appendRemainingZeros(inputString);
        assertEquals("10101100",returnedString.toString());
    }

    @Test
    public void TestappendRemainingZeros4()
    {
        StringBuilder inputString=new StringBuilder("");
        StringBuilder returnedString=c.appendRemainingZeros(inputString);
        assertEquals("",returnedString.toString());
    }



    @Test
    public void TestIterateTreeAndCalcuateHuffManMap_ForPositiveCase()
    {
        IMap imap=new CharMaps();
        String ans="";

        c.iterateTreeAndCalculateHuffManCode(root,ans,imap);
        Map<Object,String> expectedMap=new HashMap<>();
        Map<Object,String> huffmanMap=imap.returnHuffMap();
        expectedMap.put("a","0");
        expectedMap.put("b","1");
        assertEquals(expectedMap,huffmanMap);
    }

    @Test
    public void TestIterateTreeAndCalculateHuffManMap_ForNullEmpty()
    {
        String ans="";
        TreeNode root=null;
        IMap imap=new CharMaps();
        c.iterateTreeAndCalculateHuffManCode(root,ans,imap);
        Map<Object,String> HuffmanMap=imap.returnHuffMap();
        assertTrue(HuffmanMap.isEmpty());
    }


    @Test
    public void TestIterateTreeAndCalcuateHUffManMap_ForSingleNode()
    {
        String ans="";
        TreeNode singleNode =new StringTreeNode("a",2);
        singleNode.setRight(null);
        singleNode.setLeft(null);

        IMap imap=new CharMaps();


        c.iterateTreeAndCalculateHuffManCode(singleNode,ans,imap);
        Map<Object,String> huffManMap=imap.returnHuffMap();
        Map<Object, String> expectedHUffManMap=new HashMap<>();
        expectedHUffManMap.put("a","");
        assertEquals(expectedHUffManMap,huffManMap);
    }




    @Test
    public void TestCompressMethodForPositiveCase()
    {
        StringBuilder inputString=new StringBuilder("0101001001111000");
        byte[] byteArray=c.compress(inputString);
        assertEquals(byteArray[0],82);
        assertEquals(byteArray[1],120);
    }


    @Test
    public void TestCompressMethodForEmmptyCase()
    {
        StringBuilder inputString=new StringBuilder("");
        byte[] byteArray=c.compress(inputString);
        assertTrue(byteArray.length==0);
    }

    @Test
    public void TestCompressMethodForPositiveCase1()
    {
        StringBuilder inputString=new StringBuilder("01010010");
        byte[] byteArray=c.compress(inputString);
        assertEquals(byteArray[0],82);

    }

    @Test
    public void TestgetCodeForPositiveCase()
    {
        IMap imap=new WordMaps();
        Map<Object,String> huffManMap=imap.returnHuffMap();
        imap.putHuffManCode("hello","10");
        imap.putHuffManCode("world","111");
        imap.putHuffManCode("this","01");
        imap.putHuffManCode("is","00");
        imap.putHuffManCode("capillary","110");
        imap.putHuffManCode(" ","0");

        IDataHandle ifile=new StringHandler("hello world this is capillary");
        StringBuilder returnedStringBuilder=c.getCodes(imap,ifile);

        StringBuilder expectedStringBuilder=new StringBuilder("1001110010000110");
        assertEquals(returnedStringBuilder.toString(),expectedStringBuilder.toString());
    }


    @Test
    public void TestgetCodesForCase_ThereisNoMatchBetweenInputAndMap()
    {
        IMap imap=new WordMaps();
      //  Map<Object,String> huffManMap=imap.returnHuffMap();
        StringBuilder expected = new StringBuilder();
        imap.putHuffManCode("a","0");
       // huffManMap.put("a","0");
        IDataHandle iFile=new StringHandler("A");
        StringBuilder result= c.getCodes(imap,iFile);
        System.out.println(result);
        assertTrue(expected.length()==result.length());
    }



 */


}