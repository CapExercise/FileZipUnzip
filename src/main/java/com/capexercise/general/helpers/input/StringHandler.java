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

    public StringHandler(String source) {
        strData = new StringBuilder(source);
    }

    public StringHandler(byte[] byteArray){
        String temp = new String(byteArray);
        strData = new StringBuilder(temp);
    }

    @Override
    public String readContent() {

        return strData.toString();
    }

    @Override
    public String[] readContentAsArray() {

        List<String> stringList = new ArrayList<>();



            int i=0;
           while(i<strData.length()) {
               String sub="";
               int j=i;


               while (j<strData.length() && (Character.isAlphabetic(strData.charAt(j)) || Character.isDigit(strData.charAt(j)))) {
                   sub += strData.charAt(j++);
//
               }

               if (sub.length() != 0)
                   stringList.add(sub);

               if (j != strData.length())
                   stringList.add("" + strData.charAt(j));

               i=j+1;

           }

        String[] result = new String[(int) stringList.size()];
        for (int k = 0; k < result.length; k++)
            result[k] = stringList.get(k);

        return result;

    }

    @Override
    public void formMap() {
//        freqMap = new HashMap<>();
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
////                    stringList.add(sub);
//                    freqMap.put(sub, freqMap.getOrDefault(sub, 0)+1);
//                if (val != -1)
////                    stringList.add("" + character);
//                    freqMap.put(""+character, freqMap.getOrDefault(""+character, 0)+1);
//
//                sub = "";
//                val = fin.read();
//            }
//            //System.out.println(ans.toString());
//            fin.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

    }

    @Override
    public void setPercentage(int per) {

    }

    @Override
    public int getPercentage() {
        return 0;
    }

    public Map<Object, Integer> returnMap(){
        return freqMap;
    }
}
