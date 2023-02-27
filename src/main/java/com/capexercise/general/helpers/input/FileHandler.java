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

    int per;

    public FileHandler(String path) {
        filObj = new File(path);
        fileContents  = new String[0];
    }

    @Override
    public String readContent() {
        // System.out.println("hello");
        StringBuilder ans = new StringBuilder();
        try {

            FileReader fin = new FileReader(filObj);
            int c = fin.read();
            while (c != -1) {
                ans.append((char) c);
                // System.out.print((char)c);
                c = fin.read();
            }
            //System.out.println(ans.toString());
            fin.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ans.toString();
    }

    @Override
    public String[] readContentAsArray() {
//        List<String> stringList = new ArrayList<>();
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
//                if (sub.length() != 0)
//                    stringList.add(sub);
//                if (val != -1)
//                    stringList.add("" + character);
//
//                sub = "";
//                val = fin.read();
//            }
//            //System.out.println(ans.toString());
//            fin.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        String[] result = new String[(int) stringList.size()];
//        for (int i = 0; i < result.length; i++)
//            result[i] = stringList.get(i);
//
//        return result;
        return this.fileContents;
    }

    public void formMap(){
        List<String> stringList = new ArrayList<>();
        freqMap = new HashMap<>();
        try {

            FileReader fin = new FileReader(filObj);
            int val = fin.read();
            String sub = "";
            while (val != -1) {
                char character = (char) val;
                while (Character.isAlphabetic(character) || Character.isDigit(character)) {
                    sub += character;
                    val = fin.read();
                    character = (char) val;
                }

                if (sub.length() != 0) {
                    stringList.add(sub);
                    freqMap.put(sub, freqMap.getOrDefault(sub, 0) + 1);
                }
                if (val != -1) {
                    stringList.add("" + character);
                    freqMap.put("" + character, freqMap.getOrDefault("" + character, 0) + 1);
                }
                sub = "";
                val = fin.read();
            }
            //System.out.println(ans.toString());
            fin.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        fileContents = stringList.toArray(fileContents);
    }

//    public void formMap(){
//        List<String> stringList = new ArrayList<>();
//        freqMap = new HashMap<>();
//        try {
//            byte[] inputArray=new byte[(int)filObj.length()];
//            FileInputStream fin=new FileInputStream(filObj);
//            fin.read(inputArray);
//            fin.close();
//
//            int idx=0;
//            String sub = "";
//            while(idx<inputArray.length) {
//                char character = (char) ((inputArray[idx]+256)%256);
//                while (Character.isAlphabetic(character) || Character.isDigit(character)) {
//                    sub += character;
//                    character = (char) ((inputArray[++idx]+256)%256);
//                }
//
//                if (sub.length() != 0) {
//                    stringList.add(sub);
//                    freqMap.put(sub, freqMap.getOrDefault(sub, 0) + 1);
//                }
//                if (idx != inputArray.length) {
//                    stringList.add("" + character);
//                    freqMap.put("" + character, freqMap.getOrDefault("" + character, 0) + 1);
//                }
//                sub = "";
//                idx++;
//            }
//            //System.out.println(ans.toString());
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        fileContents = new String[stringList.size()];
//        for(int i=0;i<fileContents.length;i++)
//            fileContents[i] = stringList.get(i);
//
//        System.out.println("size of formed frequency map:"+freqMap.size());
//
//    }

    @Override
    public void setPercentage(int per) {
        this.per=per;
    }



    public int getPercentage()
    {
        return this.per;
    }

    public Map<Object, Integer> returnMap(){
        return this.freqMap;
    }
}
