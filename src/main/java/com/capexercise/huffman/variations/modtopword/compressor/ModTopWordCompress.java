package com.capexercise.huffman.variations.modtopword.compressor;

import com.capexercise.general.FileContents;
import com.capexercise.general.IFileContents;
import com.capexercise.general.helpers.input.IDataHandle;
import com.capexercise.general.helpers.maps.IMap;
import com.capexercise.general.helpers.maps.WordMaps;
import com.capexercise.general.helpers.nodes.TreeNode;
import com.capexercise.huffman.compression.ICompress;
import com.capexercise.huffman.general.auxiliary.GeneralMethods;
import com.capexercise.huffman.general.auxiliary.IGeneral;

public class ModTopWordCompress implements ICompress {

    IGeneral method;

    public IMap calculateFreq(IDataHandle dataObj) {

        IMap imap = new WordMaps();
//        dataObj.formMap();
        String fileContents = dataObj.readContent();
        String sub  = "";

        for(int i=0;i< fileContents.length();i++) {
            char character = fileContents.charAt(i);
            while (Character.isAlphabetic(character) || Character.isDigit(character)) {
                sub += character;
                character = fileContents.charAt(++i);
            }

            if (sub.length() != 0) {
//                   freqMap.put(sub, freqMap.getOrDefault(sub, 0) + 1);
                imap.putFrequency(sub,imap.getFrequency(sub));
            }
            if (i != fileContents.length()) {
                // freqMap.put("" + character, freqMap.getOrDefault("" + character, 0) + 1);
                imap.putFrequency(String.valueOf(character),imap.getFrequency(String.valueOf(character)));
            }
            sub = "";
        }
        fileContents = null;
//        imap.setFreqMap(dataObj.returnMap());
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

        System.out.println("entered compress");

      //  String[] fileContents = dataObj.readContentAsArray();
        String fileContents=dataObj.readContent();
        System.out.println("file contents read");

        int size = method.getCodeSize(iMap);

        if(size%8 != 0)
            size = (size/8) +1;
        else
            size /= 8;

        System.out.println("size calculated");

        byte[] byteArray = new byte[size];
        String curCode = "",str="";
        int idx = 0;


        System.out.println("forming byte array...");
        for (int i=0;i<fileContents.length();i++) {

            char character = fileContents.charAt(i);
            while (Character.isAlphabetic(character) || Character.isDigit(character)) {
                str += character;
                character = fileContents.charAt(++i);
            }


            if(iMap.containsHuffKey(str))
                curCode += iMap.getHuffmanCode(str);
            else{
                for(char ch:str.toCharArray())
                    curCode += iMap.getHuffmanCode(String.valueOf(ch));
            }
            str = "";
            curCode +=iMap.getHuffmanCode(String.valueOf(character));

            while (curCode.length() > 8) {
                byte curByte = (byte) Integer.parseInt(curCode.substring(0, 8), 2);
                curCode = curCode.substring(8);
                byteArray[idx++] = curByte;

            }

        }

        fileContents = null;

        int extraBits = 0;

        if (!curCode.equals("")) {
            extraBits = 8 - curCode.length();
            for (int i = 0; i < extraBits; i++)
                curCode += '0';
            byteArray[idx] = (byte) Integer.parseInt(curCode.substring(0, 8), 2);
        }

        System.out.println("byte array ready!");

        IFileContents compressedData = new FileContents();


    //    compressedData.setFrequencyMap(iMap.returnFreqMap());
        compressedData.setExtraBits(extraBits);
        compressedData.setByteArray(byteArray);

        byteArray=null;

        return compressedData;

    }

}