package com.capexercise.huffman.variations.modtopword.compressor;

import com.capexercise.general.helpers.maps.CharMaps;
import com.capexercise.general.helpers.maps.IMap;
import com.capexercise.general.helpers.maps.WordMaps;
import com.capexercise.general.helpers.nodes.StringTreeNode;
import com.capexercise.general.helpers.nodes.TreeNode;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntConsumer;

import static org.junit.Assert.*;

public class CompressionThreadTest {

    CompressionThread testRef=new CompressionThread();

    TreeNode newRoot=null;
    @Before
    public void setUp()
    {
      TreeNode leftNode=new StringTreeNode("Hello",1);
      TreeNode rightNode=new StringTreeNode("World",1);
      newRoot=new StringTreeNode("-", leftNode.getFrequency()+ rightNode.getFrequency());
      newRoot.setLeft(leftNode);
      newRoot.setRight(rightNode);

    }
    @Test
    public void testConstructTree()
    {

        IMap map=new WordMaps();
        Map<Object,Integer> frequencyMap=new HashMap<>();
        frequencyMap.put("a",1);
        frequencyMap.put("b",2);
        map.setFreqMap(frequencyMap);
        TreeNode root=testRef.constructTree(map);
        assertEquals(root.getLeft().getFrequency(),1);
        assertEquals(root.getRight().getFrequency(),2);
        assertEquals(root.getLeft().getVar(),"a");
        assertEquals(root.getRight().getVar(),"b");
    }

    @Test
    public void testGetOptimalMap()
    {
        IMap map=new WordMaps();
        Map<Object,Integer> frequencyMap=new HashMap<>();
        frequencyMap.put("HELLO",1);
        frequencyMap.put("WORLD",2);
        map.setFreqMap(frequencyMap);
       List<Object> keys=new ArrayList<>();
       keys.add("HELLO");
       keys.add("WORLD");
      IMap returnedMap =testRef.getOptimalMap(map,100,keys);
       assertEquals(frequencyMap,returnedMap.returnFreqMap());
    }

    @Test
    public void testCall() throws Exception
    {
        CompressionThread mockedSpyRef= Mockito.spy(CompressionThread.class);
        IMap map=new WordMaps();
        Map<Object,Integer> frequencyMap=new HashMap<>();
        frequencyMap.put("HELLO",1);
        frequencyMap.put("WORLD",1);
        map.setFreqMap(frequencyMap);
        List<Object> keys=new ArrayList<>();
        keys.add("HELLO");
        keys.add("WORLD");
        IMap map1=new WordMaps();
        map1.setFreqMap(frequencyMap);


       CompressionThread testRef=new CompressionThread(map,100,keys);

         CompressionInfo ci=testRef.call();

        Map<Object,Integer> returnedMap=ci.getFreqMap();
        assertEquals(returnedMap,frequencyMap);
        assertEquals(100,ci.perc);

    }




}