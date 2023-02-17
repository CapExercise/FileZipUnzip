package com.capexercise.general.helpers.maps;

import java.util.HashMap;
import java.util.Map;

public class MapImplementationForWord implements IMap{

    Map<String,Integer> frequecyMap;
    Map<String,String> huffmanMap;
    public MapImplementationForWord()
    {
        frequecyMap=new HashMap<>();
        huffmanMap =new HashMap<>();
    }

    public MapImplementationForWord(Map<String,Integer> fMap,Map<String,String> hMap)
    {
        this.frequecyMap=fMap;
        this.huffmanMap=hMap;
    }

    @Override
    public void putFrequency(String val,int x) {
        frequecyMap.put(val,x+1);
    }

    @Override
    public int getFrequency(String val) {
        return frequecyMap.getOrDefault(val,0);
    }

    @Override
    public void putHuffManCode(String val, String code) {
        huffmanMap.put(val,code);
    }

    @Override
    public String getHUffmanCode(String val) {
        return huffmanMap.getOrDefault(val,"");
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
