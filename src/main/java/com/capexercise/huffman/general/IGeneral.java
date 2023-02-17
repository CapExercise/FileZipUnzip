package com.capexercise.huffman.general;

import com.capexercise.general.IFileContents;

import java.io.File;
import java.io.IOException;

public interface IGeneral {
    boolean check(String ipFilepath, String decompressedFilePath) throws IOException;

    void displayStats(String inputFilePath, String compressedFilePath, String decompressedFilePath);

    IFileContents extractContents(File fileObj);

    void addCompressedContents(IFileContents fileContents);
}
