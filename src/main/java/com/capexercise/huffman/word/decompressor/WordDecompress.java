package com.capexercise.huffman.word.decompressor;

import com.capexercise.general.Path;
import com.capexercise.general.helpers.nodes.TreeNode;
import com.capexercise.huffman.decompression.IDecompress;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class WordDecompress implements IDecompress {
    @Override
    public ArrayList<Integer> get8bitcode(int val) throws RuntimeException {
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
    public void writeIntoDecompressedFile(TreeNode root, StringBuilder decoded, int noOfZeros) {
        TreeNode node=root;
        StringBuilder finalAns = new StringBuilder();
        for (int i = 0; i < decoded.length() - noOfZeros; i++) {
            char cc = (decoded.charAt(i));

            if (cc == '0')
                node= node.getLeft();
            else
                node = node.getRight();

            if (node.getLeft() == null && node.getRight() == null) {
                finalAns.append((String) node.getVar());
                node=root;
            }

        }
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(Path.decompressedFilePath);
            fileWriter.write(finalAns.toString());
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
