package com.capexercise.decompression;

import com.capexercise.general.Node;
import com.capexercise.general.Path;
import generalPackage.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;


public class ImplemenatationClassForDecompression implements Decompress
{
    @Override
    public ArrayList<Integer> get8bitcode(int val)
    {
        if(val<0)
        {
          throw new RuntimeException();
        }
        //this method will do the decimal to binary conversion( 8 bit code)
        ArrayList<Integer> ans=new ArrayList<>();
        while(val!=0)
        {
            ans.add(val%2);
            val=val/2;
        }
        if(ans.size()<8)
        {
            while(ans.size()<8)
            {
                ans.add(0);
            }
        }
        Collections.reverse(ans);

        return ans;
    }
    @Override
    public Node goLeftorRightAndReturnNode(Node root, char val)
    {
        if(val=='0')
        {
            root=root.left;
            return root;
        }
        else
        {
            root=root.right;
            return root;
        }
    }

    @Override
    public StringBuilder getDecodedString(byte[] byteArray)
    {
        StringBuilder decoded = new StringBuilder();

        for (byte x : byteArray) {

            int val = x;
            ArrayList<Integer> newip = null;
            newip = this.get8bitcode(val < 0 ? val + 256 : val);
            for (int m = 0; m < 8; m++) {
                decoded.append(newip.get(m));
            }
        }

        return decoded;
    }

    @Override
    public void writeIntoDecompressedFile(Node root, StringBuilder decoded, int no_of_zeros) throws IOException
    {
     //01101000
        Node head=root;
            FileWriter fileWriter = new FileWriter(Path.decompressedFilePath);
            for (int i = 0; i < decoded.length() - no_of_zeros; i++) {
                char cc = (decoded.charAt(i));
                Node newNode = goLeftorRightAndReturnNode(root, cc);
                if (newNode.left == null && newNode.right == null)
                {
                    fileWriter.write(newNode.var);
                    root = head;
                }
                else
                {
                    root = newNode;
                }
            }
            fileWriter.close();

    }
}