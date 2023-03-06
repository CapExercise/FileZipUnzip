package com.capexercise.huffman.variations.word;

import com.capexercise.filezipunzip.FileZipper;
import com.capexercise.general.helpers.maps.CharMaps;
import com.capexercise.general.helpers.maps.IMap;
import com.capexercise.general.helpers.maps.WordMaps;
import com.capexercise.general.helpers.nodes.TreeNode;
import com.capexercise.huffman.variations.character.CharZipperUnZipper;
import org.junit.After;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class WordZipperUnZipperTest {


    WordZipperUnZipper testRef=new WordZipperUnZipper();
    @Test
    public void test() throws SQLException, IOException, ClassNotFoundException {
        FileZipper mySpy= Mockito.spy(WordZipperUnZipper.class);
        mySpy.compress();
        Mockito.verify(mySpy, Mockito.times(1)).compress();
    }


    @Test
    public void testConstructTree()
    {
        IMap map=new WordMaps();
        map.putFrequency("a",1);
        TreeNode root= testRef.constructTree(map);
        assertTrue(root.getLeft().getFrequency()==1);
        assertEquals(root.getLeft().getVar(),"a");

    }

    @After
    public void testDecompress() throws SQLException, IOException, ClassNotFoundException {
        FileZipper mySpy= Mockito.spy(WordZipperUnZipper.class);
        mySpy.decompress();
        Mockito.verify(mySpy, Mockito.times(1)).decompress();
    }


}