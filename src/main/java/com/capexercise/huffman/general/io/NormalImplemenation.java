package com.capexercise.huffman.general.io;

import com.capexercise.general.FileContents;
import com.capexercise.general.IFileContents;
import com.capexercise.general.Path;
import com.capexercise.huffman.general.io.InputOutput;

import java.io.*;
import java.util.Map;

public class NormalImplemenation implements InputOutput {
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
