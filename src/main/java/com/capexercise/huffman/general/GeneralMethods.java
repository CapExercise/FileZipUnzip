package com.capexercise.huffman.general;

import com.capexercise.general.FileContents;
import com.capexercise.general.IFileContents;
import com.capexercise.general.Path;

import java.io.*;
import java.util.Map;

public class GeneralMethods implements IGeneral {

    @Override
    public boolean check(String ipFilepath, String decompressedFilePath) throws IOException {
        FileReader f3 = new FileReader(ipFilepath);
        FileReader f4 = new FileReader(decompressedFilePath);

        int val1 = f3.read();
        int val2 = f4.read();
        while (val1 != 1 && val2 != -1) {

            if (val1 != val2) {
                System.out.println((char) val1);
                System.out.println((char) val2);
                return false;
            }
            val1 = f3.read();
            val2 = f4.read();
        }

        if ((val1 != -1 && val2 == -1) || (val1 == -1 && val2 != -1)) {
            System.out.println((char) val1);
            System.out.println((char) val2);
            return false;
        }

        return true;
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
    public IFileContents extractContents(File fileObj) {
        IFileContents fileContents;

        try {

            FileInputStream fin = new FileInputStream(fileObj);
            ObjectInputStream obj = new ObjectInputStream(fin);

            fileContents = new FileContents();

            fileContents.setFrequencyMap((Map<Object, Integer>) obj.readObject());
            fileContents.setExtraBits(obj.readInt());
            fileContents.setByteArray((byte[]) obj.readObject());

            obj.close();
            fin.close();


        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return fileContents;
    }

    @Override
    public void addCompressedContents(IFileContents fileContents) {
        String outputFilePath = Path.compressedFilePath;
        File newFile = new File(outputFilePath);


        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(newFile);
            ObjectOutputStream obj = new ObjectOutputStream(fout);

            obj.writeObject(fileContents.getFrequencyMap());
            obj.writeInt(fileContents.getExtraBits());
            obj.writeObject(fileContents.getByteArray());

            obj.flush();

            obj.close();
            fout.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
