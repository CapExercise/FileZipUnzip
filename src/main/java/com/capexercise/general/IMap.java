package com.capexercise.general;

import java.util.Map;
import java.util.Set;

public interface IMap {

    void putFrequency(String val,int x);

    int getFrequency(String val);




    void putHuffManCode(String val,String code);

    String getHUffmanCode(String val);

    Object returnMap();

    int freqSize();

    int huffmanSize();


}
