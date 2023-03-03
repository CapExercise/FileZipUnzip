package com.capexercise.general.helpers.checksum;

import com.capexercise.general.helpers.checksum.IKeyGenerator;
import com.capexercise.general.helpers.checksum.MD5KeyGenerator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MD5KeyGeneratorTest {

    IKeyGenerator testRef;
    String filePath, fileKey;

    @Before
    public void setup(){
        testRef = new MD5KeyGenerator();
        filePath = "src/main/java/com/capexercise/Files/bigfile.txt";
        fileKey = "4defd61945cb1776c653836e75d0112e";
    }

    @Test
    public void testGenerateKey(){
        String actual = testRef.generateKey(filePath);
        assertEquals(fileKey, actual);
    }

    @Test(expected = RuntimeException.class)
    public void testGenerateKeyNegative(){
        String actual = testRef.generateKey("src/main/java/com/capexercise/Files/nonexistent.txt");
        assertEquals(fileKey, actual);
    }

}