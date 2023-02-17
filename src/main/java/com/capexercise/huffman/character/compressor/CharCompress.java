package com.capexercise.huffman.character.compressor;

import com.capexercise.general.helpers.input.IDataHandle;
import com.capexercise.general.helpers.maps.CharMaps;
import com.capexercise.general.helpers.maps.IMap;
import com.capexercise.general.helpers.nodes.TreeNode;
import com.capexercise.huffman.compression.ICompress;

public class CharCompress implements ICompress {
    @Override
    public IMap calculateFreq(IDataHandle fileReader) {
        // Map<Character, Integer> frequencyMap = new HashMap<>();
        IMap imap = new CharMaps();

        int c = 0;

        String ans = fileReader.readContent();

        for (char x : ans.toCharArray()) {
            int val = imap.getFrequency(x);
            imap.putFrequency(x, val);
            //frequencyMap.put(x,frequencyMap.getOrDefault(x,0)+1);
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
        if (coded.length() == 0 | coded.length() % 8 == 0) {
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
            //  huffmanMap.putHuffManCode((String) newNode.getVar(),s);
            huffmanMap.putHuffManCode(newNode.getVar(), s);
        }
        iterateTreeAndCalculateHuffManCode(newNode.getLeft(), s + "0", huffmanMap);
        iterateTreeAndCalculateHuffManCode(newNode.getRight(), s + "1", huffmanMap);
    }

    @Override
    public StringBuilder getCodes(IMap huffmanMap, IDataHandle fobj) {
        StringBuilder ans = new StringBuilder();
        String curr = fobj.readContent();
        for (char x : curr.toCharArray()) {
            ans.append(huffmanMap.getHuffmanCode(x));
        }
        return ans;
    }

}
