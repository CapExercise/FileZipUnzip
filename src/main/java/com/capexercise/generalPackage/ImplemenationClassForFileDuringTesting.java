package com.capexercise.generalPackage;

public class ImplemenationClassForFileDuringTesting implements IFileReader {
    StringBuilder testingCotent;

    public ImplemenationClassForFileDuringTesting(String source)
    {
        testingCotent=new StringBuilder(source);
    }
    @Override
    public String readFile()
    {
       return testingCotent.toString();
    }
}
