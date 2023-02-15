package com.capexercise.general;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GeneralClass
{
    public static boolean check(String ipFilepath,String decompressedFilePath) throws IOException
    {
        FileReader f3=new FileReader(ipFilepath);
        FileReader f4=new FileReader(decompressedFilePath);

        int val1 = f3.read();
        int val2 = f4.read();
        while (val1 != 1 && val2 != -1)
        {

            if(val1!=val2)
            {
                System.out.println((char) val1);
                System.out.println((char)val2);
               return false;
            }
            val1=f3.read();
            val2=f4.read();
        }

        if((val1!=-1 && val2==-1) || (val1==-1 && val2!=-1))
        {
            System.out.println((char)val1);
            System.out.println((char)val2);
            return false;
        }

        return true;
    }

    public static void displayStats(String inputFilePath, String compressedFilePath, String decompressedFilePath)
    {
        File ipFile=new File(inputFilePath);
        File compressedFile=new File(compressedFilePath);
        File decompressedFiled=new File(decompressedFilePath);

        long ipFilesize=ipFile.length();
        long compressedFilesize=compressedFile.length();
        long decompressedFilesize=decompressedFiled.length();

        final String BYTES= "bytes ";
        System.out.println("Input File size is "+ipFilesize+" "+BYTES);

        System.out.println("Compressed File size is "+compressedFilesize+" "+BYTES);
        System.out.println("Decompressed File size is "+decompressedFilesize+" "+BYTES);


        double ans= (ipFilesize-compressedFilesize);
        ans=((ans)/(ipFilesize));
        System.out.println("       ");
        System.out.println("        ");
        System.out.println("Compression Percentage "+ans*100+" %");
    }
}
