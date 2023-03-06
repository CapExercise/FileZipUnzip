package com.capexercise.general.helpers.input;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class FileHandler implements IDataHandle {
    File fileObj;

    int percentage;
    public FileHandler(String path)
    {
        fileObj = new File(path);
    }

    @Override
    public String readContent() {

        String strData = "";
        try {
            byte[] byteArray = new byte[(int) fileObj.length()];
            FileInputStream fin = new FileInputStream(this.fileObj);
            fin.read(byteArray);
            fin.close();
            strData = new String(byteArray, "UTF-8");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return strData;


    }


    @Override
    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public int getPercentage() {
        return this.percentage;
    }




}
