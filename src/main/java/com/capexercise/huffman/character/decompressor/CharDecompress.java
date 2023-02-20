package com.capexercise.huffman.character.decompressor;

import com.capexercise.general.Path;
import com.capexercise.general.helpers.nodes.TreeNode;
import com.capexercise.huffman.decompression.IDecompress;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class CharDecompress implements IDecompress {
    @Override
    public ArrayList<Integer> get8bitcode(int val) {
        if (val < 0) {
            throw new RuntimeException();
        }
        //this method will do the decimal to binary conversion( 8 bit code)
        ArrayList<Integer> ans = new ArrayList<>();
        while (val != 0) {
            ans.add(val % 2);
            val = val / 2;
        }
        if (ans.size() < 8) {
            while (ans.size() < 8) {
                ans.add(0);
            }
        }
        Collections.reverse(ans);

        return ans;
    }

    @Override
    public TreeNode goLeftorRightAndReturnNode(TreeNode root, char val) {
        if (val == '0') {
            root = root.getLeft();
            return root;
        } else {
            root = root.getRight();
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
    public void writeIntoDecompressedFile(TreeNode root, StringBuilder decoded, int no_of_zeros) {
        //01101000
//        TreeNode head = root;
        TreeNode node = root;
        try {
            FileWriter fileWriter = new FileWriter(Path.decompressedFilePath);
            for (int i = 0; i < decoded.length() - no_of_zeros; i++) {
                char cc = (decoded.charAt(i));
//                TreeNode newNode=null;
                if (cc == '0')
                {
                    node= node.getLeft();

                } else {
                    node = node.getRight();

                }

                if (node.getLeft() == null && node.getRight() == null) {
                    fileWriter.write((char) node.getVar());
                    node = root;
                }
            }
            fileWriter.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException();
        }
    }
}