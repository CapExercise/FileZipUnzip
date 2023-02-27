package com.capexercise.huffman.variations.modtopword.compressor;

import com.capexercise.general.FileContents;
import com.capexercise.general.IFileContents;
import com.capexercise.general.Path;
import com.capexercise.general.helpers.input.IDataHandle;
import com.capexercise.general.helpers.maps.IMap;
import com.capexercise.general.helpers.maps.WordMaps;
import com.capexercise.general.helpers.nodes.TreeNode;
import com.capexercise.huffman.compression.ICompress;
import com.capexercise.huffman.general.GeneralMethods;
import com.capexercise.huffman.general.IGeneral;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ModTopWordCompress implements ICompress {

    IGeneral method;

    public IMap calculateFreq(IDataHandle dataObj) {

        IMap imap = new WordMaps();
        dataObj.formMap();
        imap.setFreqMap(dataObj.returnMap());
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

    public IFileContents compress(IMap iMap, IDataHandle dataObj) {

        method = new GeneralMethods();

        String[] fileContents = dataObj.readContentAsArray();
//
//        System.out.println("read file contents");
//        System.out.println(fileContents.length);
        int size = method.getCodeSize(iMap);

        if(size%8 != 0)
            size = (size/8) +1;
        else
            size /= 8;

        byte[] byteArray = new byte[size];
        String curCode = "";
        int idx = 0;

        for (String str:fileContents) {

            if(iMap.containsHuffKey(str))
                curCode += iMap.getHuffmanCode(str);
            else{
                for(char character:str.toCharArray())
                    curCode += iMap.getHuffmanCode(String.valueOf(character));
            }

            while (curCode.length() > 8) {
                byte curByte = (byte) Integer.parseInt(curCode.substring(0, 8), 2);
                curCode = curCode.substring(8);
                byteArray[idx++] = curByte;

            }

        }

//        System.out.println("done");

        int extraBits = 0;

        if (!curCode.equals("")) {
            extraBits = 8 - curCode.length();
            for (int i = 0; i < extraBits; i++)
                curCode += '0';
            byteArray[idx] = (byte) Integer.parseInt(curCode.substring(0, 8), 2);
        }
//
//        System.out.println(extraBits);
//        System.out.println(byteArray.length);


        IFileContents compressedData = new FileContents();

        compressedData.setFrequencyMap(iMap.returnFreqMap());
        compressedData.setExtraBits(extraBits);
        compressedData.setByteArray(byteArray);

        return compressedData;

    }

}