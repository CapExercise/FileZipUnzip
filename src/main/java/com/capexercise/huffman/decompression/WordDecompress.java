package com.capexercise.huffman.decompression;

import com.capexercise.general.Path;
import com.capexercise.general.TreeNode;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class WordDecompress implements Decompress{
    @Override
    public ArrayList<Integer> get8bitcode(int val) throws RuntimeException {
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
    public TreeNode goLeftorRightAndReturnNode(TreeNode root, char val) {
        if(val=='0')
        {
            root=root.getLeft();
            return root;
        }
        else
        {
            root=root.getRight();
            return root;
        }

    }

    @Override
    public StringBuilder getDecodedString(byte[] byteArray) {
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
    public void writeIntoDecompressedFile(TreeNode root, StringBuilder decoded, int no_of_zeros) throws IOException {
        TreeNode head=root;

        StringBuilder finalAns=new StringBuilder();
        for (int i = 0; i < decoded.length() - no_of_zeros; i++) {
            char cc = (decoded.charAt(i));
            TreeNode newNode = goLeftorRightAndReturnNode(root, cc);
            if (newNode.getLeft() == null && newNode.getRight() == null)
            {
                finalAns.append((String) newNode.getVar());
                //fileWriter.write((char)newNode.getVar());
                root = head;
            }
            else
            {
                root = newNode;
            }
        }
        FileWriter fileWriter = new FileWriter(Path.decompressedFilePath);
        fileWriter.write(finalAns.toString());
        fileWriter.close();
    }
}
