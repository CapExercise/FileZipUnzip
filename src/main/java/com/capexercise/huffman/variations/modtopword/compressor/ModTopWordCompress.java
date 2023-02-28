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

    public void iterateTreeAndCalculateHuffManCode(TreeNode newNode, String s, IMap huffmanMap) {
        if (newNode == null) {
            return;
        }
        if (newNode.getLeft() == null && newNode.getRight() == null) {
            huffmanMap.putHuffManCode(newNode.getVar(), s);
        }
        iterateTreeAndCalculateHuffManCode(newNode.getLeft(), s + "0", huffmanMap);
        iterateTreeAndCalculateHuffManCode(newNode.getRight(), s + "1", huffmanMap);
    }



    public IFileContents compress(IMap iMap, IDataHandle dataObj) {

        method = new GeneralMethods();

        String[] fileContents = dataObj.readContentAsArray();

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


        int extraBits = 0;

        if (!curCode.equals("")) {
            extraBits = 8 - curCode.length();
            for (int i = 0; i < extraBits; i++)
                curCode += '0';
            byteArray[idx] = (byte) Integer.parseInt(curCode.substring(0, 8), 2);
        }


        IFileContents compressedData = new FileContents();

        compressedData.setFrequencyMap(iMap.returnFreqMap());
        compressedData.setExtraBits(extraBits);
        compressedData.setByteArray(byteArray);

        return compressedData;

    }

}