package com.capexercise.general.helpers.filedata;

import java.util.Map;

public interface IFileContents {
    Map<Object, Integer> getFrequencyMap();

    int getExtraBits();

    byte[] getByteArray();

    void setFrequencyMap(Map<Object, Integer> obj);

    void setExtraBits(int extraBits);

    void setByteArray(byte[] byteArray);

    String getMD5key();

    void setMD5Key(String key);
}
