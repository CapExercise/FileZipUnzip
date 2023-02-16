package com.capexercise.huffman;

import java.io.IOException;

public interface GeneralInterface {
    StringBuilder appendRemainingZeros(StringBuilder coded);
    int noofZerosToBeAppended(StringBuilder coded);
    byte[] compress(StringBuilder coded);
    boolean check(String ipFilepath,String decompressedFilePath) throws IOException;
    void displayStats(String inputFilePath, String compressedFilePath, String decompressedFilePath);
}
