package com.capexercise.general.helpers.input;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHandler implements IDataHandle {
    File filObj;

    public FileHandler(String path) {
        filObj = new File(path);
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
        List<String> stringList = new ArrayList<>();
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

                if (sub.length() != 0)
                    stringList.add(sub);

                if (val != -1)
                    stringList.add("" + character);

                sub = "";
                val = fin.read();
            }
            //System.out.println(ans.toString());
            fin.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[] result = new String[(int) stringList.size()];
        for (int i = 0; i < result.length; i++)
            result[i] = stringList.get(i);

        return result;
    }
}
