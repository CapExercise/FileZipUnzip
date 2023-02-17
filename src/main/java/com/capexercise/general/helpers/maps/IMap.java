package com.capexercise.general.helpers.maps;

public interface IMap {

    void putFrequency(Object val, int x);

    int getFrequency(Object val);

    void putHuffManCode(Object val, String code);

    String getHuffmanCode(Object val);

    Object returnMap();

    int freqSize();

    int huffmanSize();

    void clearFreqMap();

    void clearHuffMap();

    boolean containsFreqKey(Object key);

    boolean containsHuffKey(Object key);


}
