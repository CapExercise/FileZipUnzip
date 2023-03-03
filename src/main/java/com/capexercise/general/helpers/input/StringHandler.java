package com.capexercise.general.helpers.input;

import javax.print.attribute.IntegerSyntax;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringHandler implements IDataHandle {
    StringBuilder strData;
    Map<Object, Integer> freqMap;

    int per;

    boolean mapFormed=false;
    List<String> stringList;

    public StringHandler(String source) {
        stringList=new ArrayList<>();
        strData = new StringBuilder(source);
    }

    public StringHandler(byte[] byteArray){
        stringList=new ArrayList<>();
        String temp = new String(byteArray);
        strData = new StringBuilder(temp);
    }

    @Override
    public String readContent() {
        return strData.toString();
    }

    @Override
    public String[] readContentAsArray() {
        if(!mapFormed)
            formMap();
        String[] result = new String[(int) stringList.size()];
        for (int k = 0; k < result.length; k++)
            result[k] = stringList.get(k);

        return result;

    }

    @Override
    public void formMap() {
        freqMap = new HashMap<>();
        int i=0;
        while(i<strData.length()) {
            String sub="";
            int j=i;


            while (j<strData.length() && (Character.isAlphabetic(strData.charAt(j)) || Character.isDigit(strData.charAt(j)))) {
                sub += strData.charAt(j++);
            }

            if (sub.length() != 0) {
                stringList.add(sub);
                freqMap.put(sub, freqMap.getOrDefault(sub, 0)+1);
            }

            if (j != strData.length()) {
                stringList.add("" + strData.charAt(j));
                freqMap.put(""+strData.charAt(j), freqMap.getOrDefault(""+strData.charAt(j), 0)+1);
            }
            i=j+1;

        }
        mapFormed = true;
    }

    @Override
    public void setPercentage(int per) {
     this.per=per;
    }

    @Override
    public int getPercentage() {
        return this.per;
    }

    @Override
    public Map<Object, Integer> returnMap(){
        return freqMap;
    }
}
