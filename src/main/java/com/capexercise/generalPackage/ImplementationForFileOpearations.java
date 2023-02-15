package com.capexercise.generalPackage;

import java.io.*;

public class ImplementationForFileOpearations implements IFileReader {
    File filObj;

    public ImplementationForFileOpearations(String path)
    {
        filObj=new File(path);
    }
    @Override
    public String readFile()
    {
       // System.out.println("hello");
        StringBuilder ans=new StringBuilder();
        try {
            FileReader fin=new FileReader(filObj);
            int c=fin.read();
            while(c!=-1)
            {
                ans.append((char)c);
               // System.out.print((char)c);
                c=fin.read();
            }
            //System.out.println(ans.toString());
           fin.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ans.toString();
    }
}
