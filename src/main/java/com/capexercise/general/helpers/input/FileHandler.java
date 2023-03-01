package com.capexercise.general.helpers.input;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileHandler implements IDataHandle {
    File filObj;

    String[] fileContents;

    public Map<Object, Integer> freqMap;

    boolean mapFormed=false;

    int per;

    public FileHandler(String path) {
        filObj = new File(path);
        fileContents  = new String[0];
    }

    @Override
    public String readContent() {
        StringBuilder ans = new StringBuilder();
        try {

            FileReader fin = new FileReader(filObj);
            int c = fin.read();
            while (c != -1) {
                ans.append((char) c);
                c = fin.read();
            }
            fin.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ans.toString();
    }

    @Override
    public String[] readContentAsArray() {
        if(!mapFormed)
            this.formMap();
        return this.fileContents;
    }

    @Override
//    public void formMap(){
//        List<String> stringList = new ArrayList<>();
//        freqMap = new HashMap<>();
//          long start = System.currentTimeMillis();
//        try {
//
//            FileReader fin = new FileReader(filObj);
//            int val = fin.read();
//            String sub = "";
//            while (val != -1) {
//                char character = (char) val;
//                while (Character.isAlphabetic(character) || Character.isDigit(character)) {
//                    sub += character;
//                    val = fin.read();
//                    character = (char) val;
//                }
//
//                if (sub.length() != 0) {
//                    stringList.add(sub);
//                    freqMap.put(sub, freqMap.getOrDefault(sub, 0) + 1);
//                }
//                if (val != -1) {
//                    stringList.add("" + character);
//                    freqMap.put("" + character, freqMap.getOrDefault("" + character, 0) + 1);
//                }
//                sub = "";
//                val = fin.read();
//            }
//            fin.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//         System.out.println("time to form string from byte array:"+(System.currentTimeMillis()-start));
//        fileContents = stringList.toArray(fileContents);
//        stringList.clear();
//    }
    public void formMap(){
        List<String> stringList = new ArrayList<>();
        freqMap = new HashMap<>();
        String strData = null;
        FileInputStream fin = null;

        byte[] byteArray = new byte[(int)filObj.length()];
        try {
            fin = new FileInputStream(this.filObj);
            fin.read(byteArray);
            fin.close();
            strData = new String(byteArray,"UTF-8");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        String sub  = "";

        for(int i=0;i< strData.length();i++) {
            char character = strData.charAt(i);
                while (Character.isAlphabetic(character) || Character.isDigit(character)) {
                    sub += character;
                    character = strData.charAt(++i);
                }

                if (sub.length() != 0) {
                    stringList.add(sub);
                    freqMap.put(sub, freqMap.getOrDefault(sub, 0) + 1);
                }
                if (i != byteArray.length) {
                    stringList.add("" + character);
                    freqMap.put("" + character, freqMap.getOrDefault("" + character, 0) + 1);
                }
                sub = "";
            }

        strData = null;
        fileContents = stringList.toArray(fileContents);
        stringList.clear();
        mapFormed = true;
    }



    @Override
    public void setPercentage(int per) {
        this.per=per;
    }



    public int getPercentage()
    {
        return this.per;
    }

    @Override
    public Map<Object, Integer> returnMap(){
        return this.freqMap;
    }
}
