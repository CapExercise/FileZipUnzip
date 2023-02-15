package compressionPackage;
import com.capexercise.huffman.compression.ImplementationClassForCompression;
import com.capexercise.general.IFileReader;
import com.capexercise.general.ImplemenationClassForFileDuringTesting;
import com.capexercise.general.Node;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;


public class ImplementationClassForCompressionTest
{

    ImplementationClassForCompression c=new ImplementationClassForCompression();
      Node root=null;

    @Before
    public void beforeTest()
    {
        Node leftNode=new Node();
        leftNode.setVar('a');
        leftNode.setFrequency(6);
        leftNode.setLeft(null);
        leftNode.setRight(null);

        Node rightNode=new Node();
        rightNode.setVar('b');
        rightNode.setFrequency(7);
        rightNode.setRight(null);
        rightNode.setLeft(null);

        Node rootNode=new Node();
        rootNode.setFrequency(leftNode.getFrequency()+ rightNode.getFrequency());
        rootNode.setLeft(leftNode);
        rootNode.setRight(rightNode);

        root=rootNode;
    }

    @Test
    public void testFrequencyMapForPosistiveCondition()
    {
        IFileReader iFile=new ImplemenationClassForFileDuringTesting("zzaaabbcd");
        Map<Character,Integer> returnedFrequencyMap=c.calculateFreq(iFile);
        Map<Character,Integer> expectedMap=new HashMap<>();
        expectedMap.put('a',3);
        expectedMap.put('b',2);
        expectedMap.put('c',1);
        expectedMap.put('d',1);
        expectedMap.put('z',2);
        assertEquals(expectedMap,returnedFrequencyMap);
    }

    @Test
    public void testFrequencyMapForEmptyCondition()
    {
        IFileReader iFile=new ImplemenationClassForFileDuringTesting("");
        Map<Character,Integer> returnedFrequencyMap=c.calculateFreq(iFile);
        assertTrue(returnedFrequencyMap.isEmpty());
    }

    @Test
    public void testFrequencyMapForSpecialCharacters()
    {
        IFileReader iFile=new ImplemenationClassForFileDuringTesting("eerg™");
        Map<Character,Integer> returnedFrequencyMap=c.calculateFreq(iFile);
        Map<Character,Integer> expectedMap=c.calculateFreq(iFile);
        expectedMap.put('™',1);
        expectedMap.put('e',2);
        expectedMap.put('r',1);
        assertEquals(returnedFrequencyMap,expectedMap);
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
    public void TestIterateTreeAndCalcuateHuffManMap_ForPositiveCase()
    {
        String ans="";
        Map<Character,String> HuffmanMap=new HashMap<>();
        c.iterateTreeAndCalculateHuffManCode(root,ans,HuffmanMap);
        Map<Character,String> expectedMap=new HashMap<>();
        expectedMap.put('a',"0");
        expectedMap.put('b',"1");
        assertEquals(HuffmanMap,expectedMap);
    }

    @Test
    public void TestIterateTreeAndCalculateHuffManMap_ForNullEmpty()
    {
       String ans="";
       Node root=null;
       Map<Character,String> HuffmanMap=new HashMap<>();
        c.iterateTreeAndCalculateHuffManCode(root,ans,HuffmanMap);
        assertTrue(HuffmanMap.isEmpty());
    }

    @Test
    public void TestIterateTreeAndCalcuateHUffManMap_ForSingleNode()
    {
        String ans="";
        Node singleNode =new Node();
        singleNode.setRight(null);
        singleNode.setLeft(null);
        singleNode.setVar('a');
        singleNode.setFrequency(2);

        Map<Character,String> huffManMap=new HashMap<>();
        c.iterateTreeAndCalculateHuffManCode(singleNode,ans,huffManMap);
       Map<Character,String> expectedHUffManMap=new HashMap<>();
       expectedHUffManMap.put('a',"");
       assertEquals(huffManMap,expectedHUffManMap);
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
    public void testAddElementsintoQueueAndReturnRoot_ForPositivecondition()
    {
        Map<Character,Integer> InputMap=new HashMap<>();

        InputMap.put('a',7);
        InputMap.put('b',4);
        InputMap.put('c',7);
        InputMap.put('d',3);
        InputMap.put('e',8);
        InputMap.put('f',5);

        Node returnedNode=c.addElementIntoQueueAndReturnRoot(InputMap);

       assertEquals(returnedNode.getFrequency(),34);
       assertEquals(returnedNode.getLeft().getFrequency(),14);
       assertEquals(returnedNode.getRight().getFrequency(),20);
    }

    @Test
    public void TestAddElementsintoQueueAndReturnRoot_ForSingleNode()
    {
        Map<Character,Integer> InputMap=new HashMap<>();
        InputMap.put('a',7);
        Node returnedNode=c.addElementIntoQueueAndReturnRoot(InputMap);
        assertEquals(returnedNode.getLeft().getVar(),'a');
        assertEquals(returnedNode.getLeft().getFrequency(),7);
    }
    @Test
    public void TestappendREianingZeros2()
    {
       StringBuilder inputString=new StringBuilder("00001");
       StringBuilder returnedString=c.appendRemainingZeros(inputString);
       assertEquals("00001000",returnedString.toString());
    }

    @Test
    public void TestappendREianingZeros1()
    {
        StringBuilder inputString=new StringBuilder("10101100");
        StringBuilder returnedString=c.appendRemainingZeros(inputString);
        assertEquals("10101100",returnedString.toString());
    }

    @Test
    public void TestappendREianingZeros3()
    {
        StringBuilder inputString=new StringBuilder("");
        StringBuilder returnedString=c.appendRemainingZeros(inputString);
        assertEquals("",returnedString.toString());
    }

@Test
    public void TestgetCodeForPositiveCase()
    {
        Map<Character,String> huffManMap=new HashMap<>();
        huffManMap.put('h',"10");
        huffManMap.put('o',"111");
        huffManMap.put('m',"01");
        huffManMap.put('i',"00");
        huffManMap.put('e',"110");
        IFileReader ifile=new ImplemenationClassForFileDuringTesting("homie");
        StringBuilder returnedStringBuilder=c.getCodes(huffManMap,ifile);
        StringBuilder expectedStringBuilder=new StringBuilder("101110100110");
        assertEquals(returnedStringBuilder.toString(),expectedStringBuilder.toString());
    }

    @Test
    public void TestgetCodesForCase_ThereisNoMatchBetweenInputAndMap()
    {
        Map<Character,String> huffManMap=new HashMap<>();
        StringBuilder expected = new StringBuilder("null");
        huffManMap.put('a',"0");
        IFileReader iFile=new ImplemenationClassForFileDuringTesting("A");
       StringBuilder result= c.getCodes(huffManMap,iFile);
        assertTrue(expected.toString().equals(result.toString()));
    }

}