package com.capexercise.general.helpers.input;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StringHandler implements IDataHandle {
    StringBuilder strData;

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
}
