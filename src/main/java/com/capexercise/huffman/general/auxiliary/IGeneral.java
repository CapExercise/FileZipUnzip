package com.capexercise.huffman.general.auxiliary;

import com.capexercise.general.helpers.maps.IMap;

import java.io.IOException;

public interface IGeneral {
    boolean check(String ipFilepath, String decompressedFilePath) throws IOException;

    void displayStats(String inputFilePath, String compressedFilePath, String decompressedFilePath);



    int getFreqSize(IMap tempMap);

    int getCodeSize(IMap tempMap);
}
