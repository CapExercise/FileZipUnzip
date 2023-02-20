package com.capexercise.huffman.topword.compressor;

import com.capexercise.general.helpers.input.IDataHandle;
import com.capexercise.general.helpers.maps.IMap;
import com.capexercise.general.helpers.maps.WordMaps;
import com.capexercise.general.helpers.nodes.TreeNode;
import com.capexercise.huffman.compression.ICompress;

import java.util.*;

public class TopWordCompress implements ICompress {
    @Override
    public IMap calculateFreq(IDataHandle dataObj) {
        IMap imap = new WordMaps();

        String[] strData = dataObj.readContentAsArray();


        for (String str : strData) {
            imap.putFrequency(str, imap.getFrequency(str));
        }

        Map<Object,Integer> freqMap = imap.returnFreqMap();

        List<Object> keys=new ArrayList<>(freqMap.keySet());

        List<Object> secondList=new ArrayList<>();

        int count=0;
        Collections.sort(keys, (a, b) -> {
            String str1 = (String) a;
            String str2 = (String) b;
            if(imap.getFrequency(a)==imap.getFrequency(b))
                return str1.compareTo(str2);
            return imap.getFrequency(b) - imap.getFrequency(a);
        });

        for(int i=0;i<(keys.size()/5);i++)
        {
            secondList.add(keys.get(i));
            count++;
        }
        Map<Object,Integer> newMap = new HashMap<>();
        for(String str:strData)
        {

            if(str.length()!=0)
            {
                if(!secondList.contains(str))
                {
                    for(int idx=0;idx<str.length();idx++)
                    {
                        newMap.put(String.valueOf(str.charAt(idx)),newMap.getOrDefault(String.valueOf(str.charAt(idx)),0)+1);
                    }
                }
                else
                    newMap.put(str,newMap.getOrDefault(str,0)+1);

            }

        }
        imap.setFreqMap(newMap);
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
//            System.out.println(newNode.getVar()+"\t"+s);
            huffmanMap.putHuffManCode(newNode.getVar(), s);
        }
        iterateTreeAndCalculateHuffManCode(newNode.getLeft(), s + "0", huffmanMap);
        iterateTreeAndCalculateHuffManCode(newNode.getRight(), s + "1", huffmanMap);
    }

    @Override
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
