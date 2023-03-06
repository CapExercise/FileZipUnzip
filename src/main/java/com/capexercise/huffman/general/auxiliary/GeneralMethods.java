package com.capexercise.huffman.general.auxiliary;

import com.capexercise.general.helpers.checksum.IKeyGenerator;
import com.capexercise.general.helpers.checksum.MD5KeyGenerator;
import com.capexercise.general.helpers.maps.IMap;
import com.capexercise.huffman.general.auxiliary.IGeneral;

import java.io.*;
import java.util.Map;

public class GeneralMethods implements IGeneral {

    @Override
    public boolean check(String ipFilepath, String decompressedFilePath) throws IOException {
       /* FileReader f3 = new FileReader(ipFilepath);
        FileReader f4 = new FileReader(decompressedFilePath);

        int val1 = f3.read();
        int val2 = f4.read();
        while (val1 != 1 && val2 != -1) {

            if (val1 != val2)
                return false;

            val1 = f3.read();
            val2 = f4.read();
        }

        if (val1 != -1 || val2 != -1)
            return false;


        return true;

        */

        IKeyGenerator key=new MD5KeyGenerator();
        String key1=key.generateKey(ipFilepath);
        String key2=key.generateKey(decompressedFilePath);
        if(key1.equals(key2))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public void displayStats(String inputFilePath, String compressedFilePath, String decompressedFilePath) {
        File ipFile = new File(inputFilePath);
        File compressedFile = new File(compressedFilePath);
        File decompressedFiled = new File(decompressedFilePath);

        long ipFilesize = ipFile.length();
        long compressedFilesize = compressedFile.length();
        long decompressedFilesize = decompressedFiled.length();

        final String BYTES = "bytes ";
        System.out.println("\n");
        System.out.println("Input File size is " + ipFilesize + " " + BYTES);

        System.out.println("Compressed File size is " + compressedFilesize + " " + BYTES);
        System.out.println("Decompressed File size is " + decompressedFilesize + " " + BYTES);


        double ans = (ipFilesize - compressedFilesize);
        ans = ((ans) / (ipFilesize));
        System.out.println("\n");
        System.out.println("Compression Percentage " + ans * 100 + " %");
    }

    @Override
    public int getCodeSize(IMap map) {
        Map<Object, Integer> freqMap = map.returnFreqMap();
        int size = 0;
        for(Map.Entry<Object,Integer> ele : freqMap.entrySet())

            size += (ele.getValue() * map.getHuffmanCode(ele.getKey()).length());

        return size;
    }

    @Override
    public int getFreqSize(IMap map) {
        int size = 0;
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ObjectOutputStream writer = new ObjectOutputStream(output);
            writer.writeObject(map.returnFreqMap());
            writer.flush();
            writer.close();
            size = output.toByteArray().length;
        }catch(IOException e){
            System.out.println("I/O Error occurred!!!");
            throw new RuntimeException();
        }
        return size;
    }
}
