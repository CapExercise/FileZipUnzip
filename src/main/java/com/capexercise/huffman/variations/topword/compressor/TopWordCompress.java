package com.capexercise.huffman.variations.topword.compressor;

import com.capexercise.general.helpers.input.IDataHandle;
import com.capexercise.general.helpers.maps.IMap;
import com.capexercise.general.helpers.maps.WordMaps;
import com.capexercise.general.helpers.nodes.TreeNode;
import com.capexercise.huffman.compression.ICompress;

import java.util.*;

public class TopWordCompress implements ICompress {
    @Override
    public IMap calculateFreq(IDataHandle dataObj) {
//        long startTIme = System.currentTimeMillis();
        IMap imap = new WordMaps();

        String[] strData = dataObj.readContentAsArray();

        long end = System.currentTimeMillis();
//        System.out.println("Time to read contents: "+(end-startTIme));

//        startTIme = System.currentTimeMillis();
        for (String str : strData) {
            imap.putFrequency(str, imap.getFrequency(str));
        }

//        end = System.currentTimeMillis();
//        System.out.println("Time to generate initial map : "+(end-startTIme));

        Map<Object,Integer> freqMap = imap.returnFreqMap();

        List<Object> keys=new ArrayList<>(freqMap.keySet());

        Set<Object> topWordSet=new HashSet<>();

//        startTIme = System.currentTimeMillis();

        Collections.sort(keys, (a, b) -> {
            String str1 = (String) a;
            String str2 = (String) b;
            if(imap.getFrequency(a)==imap.getFrequency(b))
                return str1.compareTo(str2);
            return imap.getFrequency(b) - imap.getFrequency(a);
        });

        float size= (float) (41/100.00);
        for(int i=0;i<(keys.size()*size);i++)
            topWordSet.add(keys.get(i));

//        end = System.currentTimeMillis();
//        System.out.println("Time to generate list of top words: "+(end-startTIme));

//        startTIme = System.currentTimeMillis();

        Map<Object,Integer> newMap = new HashMap<>();
        for(String str:strData)
        {

            if(str.length()!=0)
            {
                if(!topWordSet.contains(str))
                {
                    for(int idx=0;idx<str.length();idx++)
                    {
                        newMap.put(str.charAt(idx)+"",newMap.getOrDefault(str.charAt(idx)+"",0)+1);
                    }
                }
                else
                    newMap.put(str,newMap.getOrDefault(str,0)+1);

            }

        }
        imap.setFreqMap(newMap);

//        end = System.currentTimeMillis();
//        System.out.println("Time to generate final map : "+(end-startTIme));

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
