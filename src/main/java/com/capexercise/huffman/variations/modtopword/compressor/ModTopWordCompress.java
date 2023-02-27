package com.capexercise.huffman.variations.modtopword.compressor;

import com.capexercise.general.Path;
import com.capexercise.general.helpers.input.IDataHandle;
import com.capexercise.general.helpers.maps.IMap;
import com.capexercise.general.helpers.maps.WordMaps;
import com.capexercise.general.helpers.nodes.TreeNode;
import com.capexercise.huffman.compression.ICompress;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ModTopWordCompress implements ICompress {

    public IMap calculateFreq(IDataHandle dataObj) {
        long startTime;

        startTime = System.currentTimeMillis();
//
//        IMap imap = new WordMaps();
//
//        String[] strData = dataObj.readContentAsArray();
//        System.out.println("Time to read file:"+(System.currentTimeMillis()-startTime));
//
//        startTime = System.currentTimeMillis();
//        for (String str : strData) {
//            imap.putFrequency(str, imap.getFrequency(str));
//        }
//
//
//        System.out.println("Time to create inital map:"+(System.currentTimeMillis()-startTime));
        IMap imap = new WordMaps();

    try{
        FileReader fin = new FileReader(Path.inputFilePath);
        int val = fin.read();
        String sub = "";
        while (val != -1) {
            char character = (char) val;
            while (Character.isAlphabetic(character) || Character.isDigit(character)) {
                sub += character;
                val = fin.read();
                character = (char) val;
            }

            if (sub.length() != 0)
//                stringList.add(sub);
                  imap.putFrequency(sub, imap.getFrequency(sub));
            if (val != -1)
//                stringList.add("" + character);
                imap.putFrequency(String.valueOf(character), imap.getFrequency(String.valueOf(character)));


            sub = "";
            val = fin.read();
        }
        //System.out.println(ans.toString());
        fin.close();
    } catch (
    IOException e) {
        throw new RuntimeException(e);
    }

        System.out.println("Time to create inital map:"+(System.currentTimeMillis()-startTime));
        System.out.println(imap.freqSize());
        return imap;
    }


    public StringBuilder appendRemainingZeros(StringBuilder coded) {
        int rem = coded.length() % 8;
        if (rem != 0) {
            rem = 8 - rem;
            while (rem != 0) {
                coded = coded.append("0");
                rem--;
            }
        }
        return coded;
    }


    public int noofZerosToBeAppended(StringBuilder coded) {
        if (coded.length() == 0 || coded.length() % 8 == 0) {
            return 0;
        }
        return 8 - (coded.length() % 8);
    }


    public byte[] compress(StringBuilder coded) {
        byte[] bytearray = new byte[coded.length() / 8];
        StringBuilder sub = new StringBuilder();
        int bytearrayIndex = 0;
        for (int i = 0; i < coded.length(); i = i + 8) {
            int j = 0;
            while (j < 8) {
                sub = sub.append(coded.charAt(i + j));
                j++;
            }
            bytearray[bytearrayIndex] = (byte) Integer.parseInt(sub.toString(), 2);
            bytearrayIndex++;
            sub.setLength(0);
        }
        return bytearray;
    }



    public void iterateTreeAndCalculateHuffManCode(TreeNode newNode, String s, IMap huffmanMap) {
        if (newNode == null) {
            return;
        }
        if (newNode.getLeft() == null && newNode.getRight() == null) {
//            System.out.println(newNode.getVar()+"\t"+s);
            huffmanMap.putHuffManCode(newNode.getVar(), s);
        }
        iterateTreeAndCalculateHuffManCode(newNode.getLeft(), s + "0", huffmanMap);
        iterateTreeAndCalculateHuffManCode(newNode.getRight(), s + "1", huffmanMap);
    }


    public StringBuilder getCodes(IMap huffmanMap, IDataHandle fobj) {
        StringBuilder finalAns = new StringBuilder();
        String[] strData = fobj.readContentAsArray();
        for(String str:strData){
            if(huffmanMap.containsHuffKey(str))
                finalAns.append(huffmanMap.getHuffmanCode(str));
            else{
                for(char character:str.toCharArray())
                    finalAns.append(huffmanMap.getHuffmanCode(String.valueOf(character)));
            }
        }
        return finalAns;
    }

}