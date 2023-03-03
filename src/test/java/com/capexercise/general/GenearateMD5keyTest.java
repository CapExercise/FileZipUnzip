package com.capexercise.general;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

public class GenearateMD5keyTest {



    @Test
    public void testForPositiveCondition() throws NoSuchAlgorithmException, IOException {
       String expected=GenearateMD5key.generateKey("src/main/java/com/capexercise/Files/empty.txt");
      assertEquals(expected,"d41d8cd98f00b204e9800998ecf8427e");
    }

    @Test(expected =IOException.class)
    public void testForThrowinaException() throws NoSuchAlgorithmException, IOException {
        String expected=GenearateMD5key.generateKey("src/main/java/com/capexercise/Files/invaid.txt");
    }

}