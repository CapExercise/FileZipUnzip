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

    public StringHandler(String source) {
        strData = new StringBuilder(source);
    }

    @Override
    public String readContent() {
        return strData.toString();
    }


    public StringHandler(byte[] byteArray){
        String temp = new String(byteArray);
        strData = new StringBuilder(temp);
    }


    @Override
    public void setPercentage(int per) {
     this.per=per;
    }

    @Override
    public int getPercentage() {
        return this.per;
    }



}
