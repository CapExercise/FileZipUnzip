package com.capexercise.general.helpers.maps;

import java.util.Map;

public interface IMap {

    void putFrequency(Object val, int x);

    int getFrequency(Object val);

    void putHuffManCode(Object val, String code);

    String getHuffmanCode(Object val);

    Map<Object,Integer> returnFreqMap();

    Map<Object,String> returnHuffMap();

    int freqSize();

    int huffmanSize();

    void clearFreqMap();

    void clearHuffMap();

    boolean containsFreqKey(Object key);

    boolean containsHuffKey(Object key);

    void setFreqMap(Map<Object,Integer> map);

    void setHuffMap(Map<Object,String> map);


}
