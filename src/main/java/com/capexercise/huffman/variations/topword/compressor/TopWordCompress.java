package com.capexercise.huffman.variations.topword.compressor;

import com.capexercise.general.FileContents;
import com.capexercise.general.IFileContents;
import com.capexercise.general.helpers.input.IDataHandle;
import com.capexercise.general.helpers.maps.IMap;
import com.capexercise.general.helpers.maps.WordMaps;
import com.capexercise.general.helpers.nodes.TreeNode;
import com.capexercise.huffman.compression.ICompress;
import com.capexercise.huffman.general.auxiliary.GeneralMethods;
import com.capexercise.huffman.general.auxiliary.IGeneral;

import java.util.*;

public class TopWordCompress implements ICompress {
    IGeneral method = new GeneralMethods();
    @Override
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


        Map<Object,Integer> freqMap = imap.returnFreqMap();

        List<Object> keys=new ArrayList<>(freqMap.keySet());

        Set<Object> topWordSet=new HashSet<>();

        Collections.sort(keys, (a, b) -> {
            String str1 = (String) a;
            String str2 = (String) b;
            if(imap.getFrequency(a)==imap.getFrequency(b))
                return str1.compareTo(str2);
            return imap.getFrequency(b) - imap.getFrequency(a);
        });

        float size= (float) (38/100.00);
        for(int i=0;i<(keys.size()*size);i++)
            topWordSet.add(keys.get(i));

        Map<Object,Integer> newMap = new HashMap<>();
        for(Object obj:keys)
        {
            String str = (String) obj;
            if(str.length()!=0)
            {
                if(!topWordSet.contains(str))
                {   int val = imap.getFrequency(obj);
                    for(int idx=0;idx<str.length();idx++)
                    {
                        newMap.put(String.valueOf(str.charAt(idx)),newMap.getOrDefault(String.valueOf(str.charAt(idx)),0)+val);
                    }
                }
                else
                    newMap.put(str, imap.getFrequency(obj));

            }

        }

        imap.setFreqMap(newMap);

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

        compressedData.setFrequencyMap(iMap.returnFreqMap());
        compressedData.setExtraBits(extraBits);
        compressedData.setByteArray(byteArray);

        return compressedData;

    }



}
