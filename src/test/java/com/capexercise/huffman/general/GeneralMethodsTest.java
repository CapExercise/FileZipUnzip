package com.capexercise.huffman.general;

import com.capexercise.general.GeneralClass;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.*;
public class GeneralMethodsTest {

    IGeneral testRef;
    @Before
    public void beforeTest()
    {
        testRef = new GeneralMethods();
        FileWriter f1=null;
        try {
            f1 =new FileWriter("src/test/java/generalPackage/test.txt");
            f1.write("aabbcd");
            f1.flush();
            f1.close();
        }

        catch (IOException e) {
            throw new RuntimeException(e);
        }

        FileWriter f2=null;
        try {
            f2 =new FileWriter("src/test/java/generalPackage/test1.txt");
            f2.write("aabbcd");
            f2.flush();
            f2.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        FileWriter f3=null;
        try {
            f3 =new FileWriter("src/test/java/generalPackage/test2.txt");
            f3.write("aabbcdd");
            f3.flush();
            f3.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        FileWriter f4=null;
        try {
            f4 =new FileWriter("src/test/java/generalPackage/test3.txt");
            f4.write("aabbc");
            f4.flush();
            f4.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @Test
    public void TestComparaion()
    {
        try
        {
            assertTrue(testRef.check("src/test/java/generalPackage/test.txt","src/test/java/generalPackage/test1.txt"));
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void TestComparaionfornegativeSituation()
    {
        try
        {
            assertFalse(testRef.check("src/test/java/generalPackage/test2.txt","src/test/java/generalPackage/test3.txt"));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getExtractContents(){

    }

    @Test
    public void testAddCompressedComponents(){

    }

    @After
    public void afterAll()
    {
        new File("src/test/java/generalPackage/test.txt").delete();
        new File("src/test/java/generalPackage/test1.txt").delete();
        new File("src/test/java/generalPackage/test2.txt").delete();
        new File("src/test/java/generalPackage/test3.txt").delete();
    }
}