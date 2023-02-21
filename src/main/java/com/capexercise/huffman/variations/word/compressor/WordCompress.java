package com.capexercise.huffman.variations.word.compressor;

import com.capexercise.general.helpers.input.IDataHandle;
import com.capexercise.general.helpers.maps.IMap;
import com.capexercise.general.helpers.maps.WordMaps;
import com.capexercise.general.helpers.nodes.TreeNode;
import com.capexercise.huffman.compression.ICompress;

public class WordCompress implements ICompress {
    @Override
    public IMap calculateFreq(IDataHandle dataObj) {
        IMap imap = new WordMaps();

        String[] strData = dataObj.readContentAsArray();


        for (String str : strData) {
            imap.putFrequency(str, imap.getFrequency(str));
        }

        return imap;
    }

    @Override
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

    @Override
    public int noofZerosToBeAppended(StringBuilder coded) {
        if (coded.length() == 0 || coded.length() % 8 == 0) {
            return 0;
        }
        return 8 - (coded.length() % 8);
    }

    @Override
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
    public StringBuilder getCodes(IMap huffmanMap, IDataHandle fobj) {
        StringBuilder finalAns = new StringBuilder();
        String ans = fobj.readContent();
        String sub = "";
        for (int i = 0; i < ans.length(); i++) {
            while (i < ans.length() && (Character.isAlphabetic(ans.charAt(i)) || Character.isDigit(ans.charAt(i))))
                sub += ans.charAt(i++);


            if (huffmanMap.containsHuffKey(sub))
                finalAns.append(huffmanMap.getHuffmanCode(sub));
            if (i!=ans.length() && huffmanMap.containsHuffKey(String.valueOf(ans.charAt(i))))
                finalAns.append(huffmanMap.getHuffmanCode(String.valueOf(ans.charAt(i))));

            sub = "";

        }
        return finalAns;
    }

}
