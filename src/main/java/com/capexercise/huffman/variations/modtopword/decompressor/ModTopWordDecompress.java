package com.capexercise.huffman.variations.modtopword.decompressor;

import com.capexercise.general.Path;
import com.capexercise.general.helpers.nodes.TreeNode;
import com.capexercise.huffman.decompression.IDecompress;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class ModTopWordDecompress implements IDecompress {


    @Override
    public void decompress(byte[] byteArray, int noOfZeroes, TreeNode root){
            System.out.println("decompression side byte array size:"+byteArray.length);
            TreeNode node = root;
            String curCode = "";
        try {
            FileWriter fw = new FileWriter(Path.decompressedFilePath);
            for(int i=0;i< byteArray.length;i++){

                int val = byteArray[i];
                curCode = getCode((val<0)?val+256:val);

                if(i == byteArray.length-1)
                    curCode = curCode.substring(0,8-noOfZeroes);

                for(char character:curCode.toCharArray()){
                    if (character == '0')
                        node = node.getLeft();
                    else
                        node = node.getRight();


                    if (node.getLeft() == null && node.getRight() == null) {
                        fw.write((String) node.getVar());
                        node=root;
                    }
//                char cc = (decoded.charAt(i));

                }
            }
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public String getCode(int val){
        String result = "";
        while (val != 0) {
            result = (val%2) + result;
            val = val / 2;
        }
        if (result.length() < 8) {
            while (result.length() < 8) {
                result = '0' + result;
            }
        }

        return result;
    }
}