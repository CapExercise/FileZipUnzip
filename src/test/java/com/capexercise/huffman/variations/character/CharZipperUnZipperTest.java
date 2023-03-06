package com.capexercise.huffman.variations.character;

import com.capexercise.filezipunzip.FileZipper;
import com.capexercise.general.helpers.maps.CharMaps;
import com.capexercise.general.helpers.maps.IMap;
import com.capexercise.general.helpers.nodes.TreeNode;
import org.junit.After;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class CharZipperUnZipperTest {

CharZipperUnZipper testRef=new CharZipperUnZipper();
    @Test
    public void test() throws SQLException, IOException, ClassNotFoundException {
        FileZipper mySpy= Mockito.spy(CharZipperUnZipper.class);
        mySpy.compress();
        Mockito.verify(mySpy, Mockito.times(1)).compress();
    }

    @After
    public void testDecompress() throws SQLException, IOException, ClassNotFoundException {
        FileZipper mySpy= Mockito.spy(CharZipperUnZipper.class);
        mySpy.decompress();
        Mockito.verify(mySpy, Mockito.times(1)).decompress();
    }

    @Test
    public void testConstructTree()
    {
        IMap map=new CharMaps();
      map.putFrequency('a',1);
        TreeNode root= testRef.constructTree(map);
        assertTrue(root.getLeft().getFrequency()==1);
      assertEquals(root.getLeft().getVar(),'a');

    }

}