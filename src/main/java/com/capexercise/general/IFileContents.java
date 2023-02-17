package com.capexercise.general;

import java.util.Map;

public interface IFileContents {
    Map<Object, Integer> getFrequencyMap();

    int getExtraBits();

    byte[] getByteArray();

    void setFrequencyMap(Map<Object, Integer> obj);

    void setExtraBits(int extraBits);

    void setByteArray(byte[] byteArray);
}
