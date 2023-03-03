package com.capexercise.huffman.variations.modtopword.compressor;

import java.util.Map;

public class CompressionInfo {
    Map<Object, Integer> freqMap;
    Map<Object, String> huffMap;

    int perc, size;

    public CompressionInfo(int perc, int size, Map<Object, Integer> freqMap, Map<Object, String> huffMap) {
        this.size = size;
        this.perc = perc;
        this.freqMap = freqMap;
        this.huffMap = huffMap;
    }

    public Map<Object, Integer> getFreqMap() {
        return this.freqMap;
    }

    public Map<Object, String> getHuffMap() {
        return this.huffMap;
    }

    public int getSize() {
        return this.size;
    }

}