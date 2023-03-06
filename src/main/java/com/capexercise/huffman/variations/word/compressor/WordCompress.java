package com.capexercise.huffman.variations.word.compressor;

import com.capexercise.general.helpers.filedata.FileContents;
import com.capexercise.general.helpers.filedata.IFileContents;
import com.capexercise.general.helpers.input.IDataHandle;
import com.capexercise.general.helpers.maps.IMap;
import com.capexercise.general.helpers.maps.WordMaps;
import com.capexercise.general.helpers.nodes.TreeNode;
import com.capexercise.huffman.compression.ICompress;
import com.capexercise.huffman.general.auxiliary.GeneralMethods;
import com.capexercise.huffman.general.auxiliary.IGeneral;

public class WordCompress implements ICompress {
    IGeneral method;
    @Override
    public IMap calculateFreq(IDataHandle dataObj) {
        IMap imap = new WordMaps();
        String fileContents = dataObj.readContent();
        String sub  = "";

        for(int i=0;i< fileContents.length();i++) {
            char character = fileContents.charAt(i);
            while (i<fileContents.length()-1 && (Character.isAlphabetic(character) || Character.isDigit(character))) {
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



    public IFileContents compress(IMap iMap, IDataHandle dataObj) {

        method = new GeneralMethods();

        String fileContents = dataObj.readContent();

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
            while (i<fileContents.length()-1 && Character.isAlphabetic(character) || Character.isDigit(character)) {
                str += character;
                character = fileContents.charAt(++i);
            }

            curCode += iMap.getHuffmanCode(str);

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

        System.out.println("byte array ready!");

        IFileContents compressedData = new FileContents();

        compressedData.setFrequencyMap(iMap.returnFreqMap());
        compressedData.setExtraBits(extraBits);
        compressedData.setByteArray(byteArray);

        return compressedData;

    }

}
