package com.capexercise.general.helpers.maps;

import java.util.HashMap;
import java.util.Map;

public class WordMaps implements IMap {

    Map<Object, Integer> frequencyMap;
    Map<Object, String> huffmanMap;

    public WordMaps() {
        frequencyMap = new HashMap<>();
        huffmanMap = new HashMap<>();
    }

    public WordMaps(Map<Object, Integer> fMap, Map<Object, String> hMap) {
        this.frequencyMap = fMap;
        this.huffmanMap = hMap;
    }

    @Override
    public void putFrequency(Object val, int x) {
        frequencyMap.put(val, x);
    }

    @Override
    public int getFrequency(Object val) {
        return frequencyMap.getOrDefault(val, 0);
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
    public Map<Object,Integer> returnFreqMap() {
        return frequencyMap;
    }

    @Override
    public Map<Object, String> returnHuffMap() {
        return this.huffmanMap;
    }

    public int freqSize() {
        return frequencyMap.size();
    }

    @Override
    public int huffmanSize() {
        return huffmanMap.size();
    }

    @Override
    public void clearFreqMap() {
        this.frequencyMap.clear();
    }

    @Override
    public void clearHuffMap() {
        this.huffmanMap.clear();
    }

    @Override
    public boolean containsFreqKey(Object key) {
        return this.frequencyMap.containsKey(key);
    }

    @Override
    public boolean containsHuffKey(Object key) {
        return this.huffmanMap.containsKey(key);
    }

    @Override
    public void setFreqMap(Map<Object, Integer> map) {
            this.frequencyMap = map;
    }

    @Override
    public void setHuffMap(Map<Object, String> map) {
        this.huffmanMap = map;
    }
}
