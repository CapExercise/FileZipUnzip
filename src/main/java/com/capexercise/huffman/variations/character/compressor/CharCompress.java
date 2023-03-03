package com.capexercise.huffman.variations.character.compressor;

import com.capexercise.general.helpers.filedata.FileContents;
import com.capexercise.general.helpers.filedata.IFileContents;
import com.capexercise.general.helpers.input.IDataHandle;
import com.capexercise.general.helpers.maps.CharMaps;
import com.capexercise.general.helpers.maps.IMap;
import com.capexercise.general.helpers.nodes.TreeNode;
import com.capexercise.huffman.compression.ICompress;
import com.capexercise.huffman.general.auxiliary.GeneralMethods;
import com.capexercise.huffman.general.auxiliary.IGeneral;

public class CharCompress implements ICompress {
    IGeneral method;

    @Override
    public IMap calculateFreq(IDataHandle dataObj) {
        IMap imap = new CharMaps();

        String ans = dataObj.readContent();

        for (char x : ans.toCharArray())
            imap.putFrequency(x, imap.getFrequency(x) + 1);

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

        String ans = dataObj.readContent();

        int size = method.getCodeSize(iMap);

        if (size % 8 != 0)
            size = (size / 8) + 1;
        else
            size /= 8;

        byte[] byteArray = new byte[size];
        String curCode = "";
        int idx = 0;

        for (char character : ans.toCharArray()) {

            curCode += iMap.getHuffmanCode(character);

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
