package com.capexercise.huffman.compression;

import com.capexercise.general.*;

import java.util.Map;

public class WordCompress implements Compress{
    @Override
    public IMap calculateFreq(IFileReader fileReader) {
        IMap imap=new MapImplementationForWord();

        String ans=fileReader.readFile();

        String sub="";

        for(int i=0;i<ans.length();i++)
        {
            while(i<ans.length() && (Character.isAlphabetic(ans.charAt(i)) || Character.isDigit(ans.charAt(i))))
                sub += ans.charAt(i++);


            if(sub.length()!=0)
                imap.putFrequency(sub,imap.getFrequency(sub));

            if(i<ans.length())
                imap.putFrequency(""+ans.charAt(i),imap.getFrequency(""+ans.charAt(i)));

            sub = "";

        }

        return imap;
    }

    @Override
    public void iterateTreeAndCalculateHuffManCode(TreeNode newNode, String s, IMap huffmanMap) {
        if(newNode==null) {
            return;
        }
        if(newNode.getLeft()==null && newNode.getRight()==null)
        {
            huffmanMap.putHuffManCode((String) newNode.getVar(),s);
            // huffmanMap.put(newNode.getVar(),s);
        }
        iterateTreeAndCalculateHuffManCode(newNode.getLeft(),s+"0",huffmanMap);
        iterateTreeAndCalculateHuffManCode(newNode.getRight(),s+"1",huffmanMap);
    }

    @Override
    public StringBuilder getCodes(IMap huffmanMap, IFileReader fobj) {
        StringBuilder finalAns=new StringBuilder();
        String ans=fobj.readFile();
        String sub="";
//        for(int i=0;i<ans.length();i++)
//        {
//            char currentCharacter = ans.charAt(i);
//
//            while(Character.isAlphabetic(currentCharacter) || Character.isDigit(currentCharacter)){
//                sub+=currentCharacter;
//                if(i<ans.length()-1)
//                    currentCharacter =ans.charAt(++i);
//            }
//
//            if(huffmanMap.containsHuffKey(sub)) {
//
//                finalAns.append(huffmanMap.getHUffmanCode(sub));
//            }
//            if(huffmanMap.containsHuffKey(""+currentCharacter))
//                finalAns.append(huffmanMap.getHUffmanCode(""+currentCharacter));
//            sub = "";
//        }
        for(int i=0;i<ans.length();i++)
        {
            while(i<ans.length() && (Character.isAlphabetic(ans.charAt(i)) || Character.isDigit(ans.charAt(i))))
                sub += ans.charAt(i++);


            if(huffmanMap.containsHuffKey(sub))
                finalAns.append(huffmanMap.getHUffmanCode(sub));
            if(huffmanMap.containsHuffKey(""+ans.charAt(i)))
                finalAns.append(huffmanMap.getHUffmanCode(""+ans.charAt(i)));

            sub = "";

        }
        return finalAns;
    }

    @Override
    public StringBuilder appendRemainingZeros(StringBuilder coded) {
        int rem = coded.length() % 8;
        if (rem != 0)
        {
            rem = 8 - rem;
            while (rem != 0) {
                coded=coded.append("0");
                rem--;
            }
        }
        return coded;

    }

    @Override
    public int noofZerosToBeAppended(StringBuilder coded) {
        if(coded.length()==0 | coded.length()%8==0)
        {
            return 0;
        }
        return 8-(coded.length()%8);
    }

    @Override
    public byte[] compress(StringBuilder coded) {
        byte[] bytearray = new byte[coded.length() / 8];
        StringBuilder sub =new StringBuilder();
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
}
