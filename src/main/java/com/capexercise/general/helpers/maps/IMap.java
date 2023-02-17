package com.capexercise.general.helpers.maps;

public interface IMap {

    void putFrequency(String val,int x);

    int getFrequency(String val);




    void putHuffManCode(String val,String code);

    String getHUffmanCode(String val);

    Object returnMap();

    int freqSize();

    int huffmanSize();

    void clearFreqMap();

    void clearHuffMap();

    boolean containsFreqKey(Object key);

    boolean containsHuffKey(Object key);


}
