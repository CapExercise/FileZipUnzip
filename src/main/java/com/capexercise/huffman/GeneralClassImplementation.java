package com.capexercise.huffman;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GeneralClassImplementation implements GeneralInterface{
    @Override
    public StringBuilder appendRemainingZeros(StringBuilder coded) {
        int rem = coded.length() % 8;
        if (rem != 0)
        {
            rem = 8 - rem;
            while (rem != 0) {
                coded=coded.append("0");
                rem--;
            }
        }
        return coded;
    }

    @Override
    public int noofZerosToBeAppended(StringBuilder coded) {
        if(coded.length()==0 | coded.length()%8==0)
        {
            return 0;
        }
        return 8-(coded.length()%8);
    }

    @Override
    public byte[] compress(StringBuilder coded) {
        byte[] bytearray = new byte[coded.length() / 8];
        StringBuilder sub =new StringBuilder();
        int bytearrayIndex = 0;
        for (int i = 0; i < coded.length(); i = i + 8) {
            int j = 0;
            while (j < 8) {
                sub = sub.append(coded.charAt(i + j));
                j++;
            }
            bytearray[bytearrayIndex] = (byte) Integer.parseInt(sub.toString(), 2);
            bytearrayIndex++;
            sub.setLength(0);
        }
        return bytearray;
    }

    @Override
    public boolean check(String ipFilepath, String decompressedFilePath) throws IOException {
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

    @Override
    public void displayStats(String inputFilePath, String compressedFilePath, String decompressedFilePath) {
        File ipFile=new File(inputFilePath);
        File compressedFile=new File(compressedFilePath);
        File decompressedFiled=new File(decompressedFilePath);

        long ipFilesize=ipFile.length();
        long compressedFilesize=compressedFile.length();
        long decompressedFilesize=decompressedFiled.length();

        final String BYTES= "bytes ";
        System.out.println("\n");
        System.out.println("Input File size is "+ipFilesize+" "+BYTES);

        System.out.println("Compressed File size is "+compressedFilesize+" "+BYTES);
        System.out.println("Decompressed File size is "+decompressedFilesize+" "+BYTES);


        double ans= (ipFilesize-compressedFilesize);
        ans=((ans)/(ipFilesize));
        System.out.println("\n");
        System.out.println("Compression Percentage "+ans*100+" %");
    }
}
