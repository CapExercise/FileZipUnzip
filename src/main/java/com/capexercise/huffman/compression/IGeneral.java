package com.capexercise.huffman.compression;

import java.io.IOException;

public interface IGeneral {
    StringBuilder appendRemainingZeros(StringBuilder coded);
    int noofZerosToBeAppended(StringBuilder coded);
    byte[] compress(StringBuilder coded);
    boolean check(String ipFilepath,String decompressedFilePath) throws IOException;
    void displayStats(String inputFilePath, String compressedFilePath, String decompressedFilePath);
}
