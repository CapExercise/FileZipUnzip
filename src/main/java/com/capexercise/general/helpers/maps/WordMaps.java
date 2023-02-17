package com.capexercise.general.helpers.maps;

import java.util.HashMap;
import java.util.Map;

public class WordMaps implements IMap {

    Map<Object, Integer> frequecyMap;
    Map<Object, String> huffmanMap;

    public WordMaps() {
        frequecyMap = new HashMap<>();
        huffmanMap = new HashMap<>();
    }

    public WordMaps(Map<Object, Integer> fMap, Map<Object, String> hMap) {
        this.frequecyMap = fMap;
        this.huffmanMap = hMap;
    }

    @Override
    public void putFrequency(Object val, int x) {
        frequecyMap.put(val, x + 1);
    }

    @Override
    public int getFrequency(Object val) {
        return frequecyMap.getOrDefault(val, 0);
    }

    @Override
    public void putHuffManCode(Object val, String code) {
        huffmanMap.put(val, code);
    }

    @Override
    public String getHuffmanCode(Object val) {
        return huffmanMap.getOrDefault(val, "");
    }

    @Override
    public Object returnMap() {
        return frequecyMap;
    }

    public int freqSize() {
        return frequecyMap.size();
    }

    @Override
    public int huffmanSize() {
        return huffmanMap.size();
    }

    @Override
    public void clearFreqMap() {
        this.frequecyMap.clear();
    }

    @Override
    public void clearHuffMap() {
        this.huffmanMap.clear();
    }

    @Override
    public boolean containsFreqKey(Object key) {
        return this.frequecyMap.containsKey(key);
    }

    @Override
    public boolean containsHuffKey(Object key) {
        return this.huffmanMap.containsKey(key);
    }
}
