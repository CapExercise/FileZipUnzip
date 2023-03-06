package com.capexercise.huffman.variations.word.decompressor;

import com.capexercise.general.Path;
import com.capexercise.general.helpers.nodes.StringTreeNode;
import com.capexercise.general.helpers.nodes.TreeNode;
import com.capexercise.huffman.variations.topword.decompressor.TopWordDecompress;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.*;

public class WordDecompressTest {

    WordDecompress d=new WordDecompress();

    TreeNode root=null;
    TreeNode leftNode=null;
    TreeNode rightNode=null;

    @Before
    public void beforeTest()
    {
        leftNode=new StringTreeNode("Hello",1);




        rightNode=new StringTreeNode("World",1);

        TreeNode rootNode=new StringTreeNode("-",leftNode.getFrequency()+ rightNode.getFrequency());
        rootNode.setLeft(leftNode);
        rootNode.setRight(rightNode);

        root=rootNode;
    }


/*
    @Test
    public void Testget8BItCode()
    {
        ArrayList<Integer> expectedArray=new ArrayList<>();
        for(int i=0;i<8;i++)
        {
            expectedArray.add(0);
        }
        ArrayList<Integer> returnedArray= null;
        try {
            returnedArray = d.get8bitcode(0);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        assertTrue(expectedArray.equals(returnedArray));
    }


    @Test
    public void Testget8BitCode1()
    {
        ArrayList<Integer> expectedArray=new ArrayList<>();
        for(int i=0;i<6;i++)
        {
            expectedArray.add(0);
        }
        expectedArray.add(1);
        expectedArray.add(0);
        ArrayList<Integer> returnedArray= null;
        try {
            returnedArray = d.get8bitcode(2);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        assertTrue(expectedArray.equals(returnedArray));

    }

    @Test(expected = RuntimeException.class)
    public void Test8BitCode2()
    {
        ArrayList<Integer> expectedArray=new ArrayList<>();
        for(int i=0;i<8;i++)
        {
            expectedArray.add(0);
        }
        try {
            ArrayList<Integer> returnedArray=d.get8bitcode(-2);
        } catch(RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void TestgetCodedString_UsingMocking()
    {

        CharDecompress mockedDecompression= Mockito.spy(d);

        ArrayList<Integer> list1=new ArrayList<Integer>(Arrays.asList(0,1,0,1,0,0,1,0));
        ArrayList<Integer> list2=new ArrayList<Integer>(Arrays.asList(0,1,1,1,1,0,0,0));
        ArrayList<Integer> list3=new ArrayList<Integer>();

        Mockito.doReturn(list1).when(mockedDecompression).get8bitcode(82);
        Mockito.doReturn(list2).when(mockedDecompression).get8bitcode(120);
        Mockito.doReturn(list3).when(mockedDecompression).get8bitcode(0);
        assertEquals(mockedDecompression.getDecodedString(new byte[]{82}).toString(),"01010010");
        assertEquals(mockedDecompression.getDecodedString(new byte[]{82,120}).toString(),"0101001001111000");
        assertEquals(mockedDecompression.getDecodedString(new byte[]{}).toString(),"");
    }

    @Test
    public void TestwriteIntoDecompressedFile()
    {
        StringBuilder decodedString=new StringBuilder("0101001001111000");

        try {
            d.writeIntoDecompressedFile(root,decodedString,3);
            FileReader fin=new FileReader(Path.decompressedFilePath);
            StringBuilder expected=new StringBuilder();
            int c=fin.read();
            while(c!=-1)
            {
                expected.append((char)c);
                c=fin.read();
            }
            assertEquals("ababaabaabbbb",expected.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testWriteIntoDecompressedFile()
    {
        StringBuilder decodedString=new StringBuilder("01101000");//acab

       TreeNode leftNode=new CharTreeNode('a',2);


       TreeNode rightLeft=new CharTreeNode('b',3);


       TreeNode rightRight=new CharTreeNode('c',2);


       TreeNode rightNode=new CharTreeNode('-',rightRight.getFrequency()+rightLeft.getFrequency());
        rightNode.setLeft(rightLeft);
        rightNode.setRight(rightRight);



       TreeNode parent=new CharTreeNode('-',leftNode.getFrequency()+rightNode.getFrequency());
        parent.setLeft(leftNode);
        parent.setRight(rightNode);


        try
        {
            d.writeIntoDecompressedFile(parent,decodedString,2);
            FileReader fin=new FileReader(Path.decompressedFilePath);
            StringBuilder expected=new StringBuilder();
            int c=fin.read();
            while(c!=-1)
            {
                expected.append((char)c);
                c=fin.read();
            }
            assertEquals("acab",expected.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

*/

    @Test
    public void testgetCodes()
    {
        assertEquals("00000000",d.getCode(0));
        assertEquals("00001000",d.getCode(8));
    }

    @Test
    public void testdecompressforPositiveCase()
    {
        byte[] bytearray=new byte[]{82,120};
        d.decompress(bytearray,3,root);
        StringBuilder expected=new StringBuilder();
        FileReader fin= null;
        try {
            fin = new FileReader(Path.decompressedFilePath);

            int c= 0;
            try {
                c = fin.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            while(c!=-1)
            {
                expected.append((char)c);
                try {
                    c=fin.read();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        assertEquals("HelloWorldHelloWorldHelloHelloWorldHelloHelloWorldWorldWorldWorld",expected.toString());
    }

    @Test
    public void testdecompressforNoZerosAtend()
    {
        byte[] bytearray=new byte[]{82};
        d.decompress(bytearray,0,root);
        StringBuilder expected=new StringBuilder();
        FileReader fin= null;
        try {
            fin = new FileReader(Path.decompressedFilePath);

            int c= 0;
            try {
                c = fin.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            while(c!=-1)
            {
                expected.append((char)c);
                try {
                    c=fin.read();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        assertEquals("HelloWorldHelloWorldHelloHelloWorldHello",expected.toString());
    }

}