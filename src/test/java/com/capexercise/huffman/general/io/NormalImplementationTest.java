package com.capexercise.huffman.general.io;

import com.capexercise.general.helpers.filedata.FileContents;
import com.capexercise.general.helpers.filedata.IFileContents;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class NormalImplementationTest {
    InputOutput testRef, mySpy;
    Map<Object, Integer> freqMap;
    int extraBits;
    byte[] byteArray;

    IFileContents fileContents;

    File fileObj;
    @Before
    public void setup(){
        testRef = new NormalImplementation();
        mySpy = Mockito.spy(NormalImplementation.class);

        extraBits = 3;
        byteArray = new byte[]{97, 97, 98, 98, 98, 99, 99, 99, 99};

        freqMap = new HashMap<>();
        freqMap.put("hello",2);
        freqMap.put("world",3);

        fileContents = new FileContents();
        fileContents.setFrequencyMap(freqMap);
        fileContents.setExtraBits(extraBits);
        fileContents.setByteArray(byteArray);

        fileObj = new File("src/main/java/com/capexercise/Files/newFile.txt");
        try{
            ObjectOutputStream obj = new ObjectOutputStream(new FileOutputStream(fileObj));
            obj.writeObject(freqMap);
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

        Map<Object, Integer> map1 = actual.getFrequencyMap();
        Map<Object, Integer> map2 = fileContents.getFrequencyMap();

        for(Map.Entry<Object, Integer> entry: map1.entrySet()){
            if(entry.getValue().intValue() != map2.get(entry.getKey()).intValue()){
                flag = false;
                break;
            }
        }
        flag = flag && (extraBits == actual.getExtraBits());

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