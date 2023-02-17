package com.capexercise.general.helpers.input;

import java.util.ArrayList;
import java.util.List;

public class StringHandler implements IDataHandle {
    StringBuilder strData;

    public StringHandler(String source) {
        strData = new StringBuilder(source);
    }

    @Override
    public String readContent() {
        return strData.toString();
    }

    @Override
    public String[] readContentAsArray() {
        List<String> stringList = new ArrayList<>();
        String sub = "";
        for (int i = 0; i < strData.length(); i++) {
            while (i < strData.length() && (Character.isAlphabetic(strData.charAt(i)) || Character.isDigit(strData.charAt(i))))
                sub += strData.charAt(i);

            if (sub.length() != 0)
                stringList.add(sub);

            if (i < strData.length())
                stringList.add("" + strData.charAt(i));

            sub = "";
        }

        return (String[]) stringList.toArray();
    }
}
