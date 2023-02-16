package com.capexercise.general;

import java.util.HashMap;
import java.util.Map;

public class MapImplementationForWord implements IMap{

    Map<String,Integer> frequecyMap;
    Map<String,String> huffmanMap;
    MapImplementationForWord()
    {
        frequecyMap=new HashMap<>();
        huffmanMap =new HashMap<>();
    }
    @Override
    public void putFrequency(String val,int x) {
        frequecyMap.put(val,x+1);
    }

    @Override
    public int getFrequency(String val) {
        return frequecyMap.get(val);
    }

    @Override
    public void putHuffManCode(String val, String code) {
        huffmanMap.put(val,code);
    }

    @Override
    public String getHUffmanCode(String val) {
        return huffmanMap.get(val);
    }

    @Override
    public Object returnMap() {
        return huffmanMap;
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
}
