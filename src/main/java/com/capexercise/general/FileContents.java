package com.capexercise.general;

import java.util.HashMap;
import java.util.Map;

public class FileContents implements IFileContents {

    private Map<Object, Integer> freqMap;
    private int extraBits;
    private byte[] byteArray;

    String key;

    public FileContents() {
        this.freqMap = new HashMap<>();
        this.extraBits = 0;
        this.byteArray = new byte[0];
    }

    public FileContents(Map<Object, Integer> freqMap, int extraBits, byte[] byteArray) {
        this.freqMap = freqMap;
        this.extraBits = extraBits;
        this.byteArray = byteArray;
    }

    @Override
    public Map<Object, Integer> getFrequencyMap() {
        return this.freqMap;
    }

    @Override
    public int getExtraBits() {
        return this.extraBits;
    }

    @Override
    public byte[] getByteArray() {
        return this.byteArray;
    }

    @Override
    public void setFrequencyMap(Map<Object, Integer> obj) {
        this.freqMap = obj;
    }

    @Override
    public void setExtraBits(int extraBits) {
        this.extraBits = extraBits;
    }

    @Override
    public void setByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
    }

    @Override
    public String getMD5key() {
        return this.key;
    }

    @Override
    public void setMD5Key(String key) {
this.key=key;
    }
}
