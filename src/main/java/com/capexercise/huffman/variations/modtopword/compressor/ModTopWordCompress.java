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
        String fileContents = dataObj.readContent();
        String sub  = "";

        for(int i=0;i< fileContents.length();i++) {
            char character = fileContents.charAt(i);

            while (Character.isAlphabetic(character) || Character.isDigit(character)) {
                sub += character;
                character = fileContents.charAt(++i);
            }

            if (sub.length() != 0)
                imap.putFrequency(sub,imap.getFrequency(sub)+1);

            if (i != fileContents.length())
                imap.putFrequency(String.valueOf(character),imap.getFrequency(String.valueOf(character))+1);

            sub = "";

        }

        return imap;
    }

@Override
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


    @Override
    public IFileContents compress(IMap iMap, IDataHandle dataObj) {

        method = new GeneralMethods();

        String fileContents=dataObj.readContent();

        int size = method.getCodeSize(iMap);

        if(size%8 != 0)
            size = (size/8) +1;
        else
            size /= 8;

        byte[] byteArray = new byte[size];
        String curCode = "",str="";
        int idx = 0;


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


        int extraBits = 0;

        if (!curCode.equals("")) {
            extraBits = 8 - curCode.length();
            for (int i = 0; i < extraBits; i++)
                curCode += '0';
            byteArray[idx] = (byte) Integer.parseInt(curCode.substring(0, 8), 2);
        }


        IFileContents compressedData = new FileContents();

        compressedData.setExtraBits(extraBits);
        compressedData.setByteArray(byteArray);


        return compressedData;

    }

}