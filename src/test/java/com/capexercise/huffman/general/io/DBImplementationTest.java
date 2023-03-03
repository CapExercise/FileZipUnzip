package com.capexercise.huffman.general.io;

import com.capexercise.general.helpers.filedata.FileContents;
import com.capexercise.general.helpers.filedata.IFileContents;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


import static org.junit.Assert.*;

public class DBImplementationTest {

    InputOutput testRef, mySpy;
    String fileKey;
    int extraBits;
    byte[] byteArray;

    IFileContents fileContents;

    File fileObj;
    @Before
    public void setup(){
        testRef = new DBImplementation();
        mySpy = Mockito.spy(DBImplementation.class);

        extraBits = 3;
        byteArray = new byte[]{97, 97, 98, 98, 98, 99, 99, 99, 99};

        fileKey = "12e78sx56en76n31g7ic2e6b76xcby3e";

        fileContents = new FileContents();
        fileContents.setMD5Key(fileKey);
        fileContents.setExtraBits(extraBits);
        fileContents.setByteArray(byteArray);

        fileObj = new File("src/main/java/com/capexercise/Files/newFile.txt");
        try{
            ObjectOutputStream obj = new ObjectOutputStream(new FileOutputStream(fileObj));
            obj.writeObject(fileKey);
            obj.writeInt(extraBits);
            obj.writeObject(byteArray);
            obj.close();
        }catch(Exception e){
            System.out.println("file not formed");
        }
    }

    @Test
    public void testAddCompressedContents() {
        mySpy.addCompressedContents(fileContents);
        Mockito.verify(mySpy, Mockito.times(1)).addCompressedContents(fileContents);
    }

    @Test(expected = RuntimeException.class)
    public void testAddCompressedContentsException(){
        Mockito.doThrow(new IOException()).when(mySpy).addCompressedContents(fileContents);
    }

    @Test
    public void testExtractContents() {
        boolean flag = true;
        IFileContents actual = testRef.extractContents(fileObj);


        flag = (actual.getMD5key().equals(fileContents.getMD5key())) && (fileContents.getExtraBits() == actual.getExtraBits());

        byte[] actualArray = actual.getByteArray();
        byte[] expectedArray = fileContents.getByteArray();

        for(int i=0; i<actualArray.length; i++){
            if(actualArray[i] != expectedArray[i]){
                flag = flag && false;
                break;
            }
        }

        assertTrue(flag);
    }

    @Test(expected = RuntimeException.class)
    public void testExtractContentsException(){
        Mockito.when(mySpy.extractContents(fileObj)).thenThrow(new IOException());
    }

    @After
    public void finish(){
        fileObj.delete();
    }

}