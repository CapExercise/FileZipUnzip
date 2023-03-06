package com.capexercise.huffman.variations.modtopword;

import com.capexercise.filezipunzip.FileZipper;
import com.capexercise.general.helpers.maps.IMap;
import com.capexercise.general.helpers.maps.WordMaps;
import com.capexercise.general.helpers.nodes.TreeNode;
import com.capexercise.huffman.general.database.IDataBase;
import com.capexercise.huffman.variations.topword.TopWordZipperUnZipper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class ModTopWordZipperUnzipperTest
{


    ModTopWordZipperUnzipper testRef=new ModTopWordZipperUnzipper();

    IDataBase db;

    @Before
    public void test() throws SQLException, IOException, ClassNotFoundException {

        FileZipper mySpy= Mockito.spy(ModTopWordZipperUnzipper.class);
        mySpy.compress();
        Mockito.verify(mySpy, Mockito.times(1)).compress();
    }
/*
    @After
    public void testDecompress() throws SQLException, IOException, ClassNotFoundException {
    db=new SQLImplemenation();
        FileZipper mySpy= Mockito.spy(ModTopWordZipperUnzipper.class);
        mySpy.decompress();
        Mockito.verify(mySpy, Mockito.times(1)).decompress();
    }



 */

    @Test
    public void testConstructTree()

    {

        IMap map=new WordMaps();
        map.putFrequency("a",1);
        TreeNode root= testRef.constructTree(map);
        assertTrue(root.getLeft().getFrequency()==1);
        assertEquals(root.getLeft().getVar(),"a");

    }



}