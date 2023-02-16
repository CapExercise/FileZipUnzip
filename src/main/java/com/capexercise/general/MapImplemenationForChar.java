package com.capexercise.general;

import java.util.HashMap;
import java.util.Map;

public class MapImplemenationForChar implements IMap {
    Map<Character,Integer> frequecyMap;
    Map<Character,String> huffmanMap;
    public MapImplemenationForChar()
    {
     frequecyMap=new HashMap<>();
     huffmanMap =new HashMap<>();
    }

    public MapImplemenationForChar(Map<Character,Integer> fMap,Map<Character,String> hMap)
    {
        this.frequecyMap=fMap;
        this.huffmanMap=hMap;
    }


    @Override
    public void putFrequency(String val,int x) {

      frequecyMap.put(val.charAt(0),x+1);
    }

    @Override
    public int getFrequency(String val) {
     return frequecyMap.getOrDefault(val.charAt(0),0);
    }





    @Override
    public void putHuffManCode(String val,String code) {
   huffmanMap.put(val.charAt(0),code);
    }

    @Override
    public String getHUffmanCode(String val) {
        return huffmanMap.get(val.charAt(0));
    }

    @Override
    public Object returnMap() {
        return frequecyMap;
    }

    @Override
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
