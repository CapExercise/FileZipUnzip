package com.capexercise.general;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GenearateMD5key
{
    public static String generateKey(String path) throws NoSuchAlgorithmException, IOException
    {
        File file = new File(path);
        MessageDigest mdigest = MessageDigest.getInstance("MD5");



        FileInputStream fis = new FileInputStream(file);


        byte[] byteArray = new byte[1024];
        int bytesCount = 0;


        while ((bytesCount = fis.read(byteArray)) != -1)
        {
            mdigest.update(byteArray, 0, bytesCount);
        };


        fis.close();


        byte[] bytes = mdigest.digest();


        StringBuilder sb = new StringBuilder();

        // loop through the bytes array
        for (int i = 0; i < bytes.length; i++) {


            sb.append(Integer
                    .toString((bytes[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }


        return sb.toString();
    }
/*
    private static String checksum(MessageDigest digest,
                                   File file)
            throws IOException
    {
        
        FileInputStream fis = new FileInputStream(file);

        
        byte[] byteArray = new byte[1024];
        int bytesCount = 0;

     
        while ((bytesCount = fis.read(byteArray)) != -1)
        {
            digest.update(byteArray, 0, bytesCount);
        };

        
        fis.close();

       
        byte[] bytes = digest.digest();

        
        StringBuilder sb = new StringBuilder();

        // loop through the bytes array
        for (int i = 0; i < bytes.length; i++) {

           
            sb.append(Integer
                    .toString((bytes[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }

      
        return sb.toString();
    }

 */
}
