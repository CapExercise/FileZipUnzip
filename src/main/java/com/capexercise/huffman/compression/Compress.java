package com.capexercise.huffman.compression;

import com.capexercise.general.IFileReader;
import com.capexercise.general.IMap;
import com.capexercise.general.Node;

import java.util.Map;

public interface Compress
{
  IMap calculateFreq(IFileReader fileReader);
    Node addElementIntoQueueAndReturnRoot(IMap frequencyMap);
    void iterateTreeAndCalculateHuffManCode(Node newNode, String s,IMap huffmanMap);
    StringBuilder getCodes(IMap huffmanMap, IFileReader fobj);
    StringBuilder appendRemainingZeros(StringBuilder coded);
    int noofZerosToBeAppended(StringBuilder coded);
    byte[] compress(StringBuilder coded);
}
