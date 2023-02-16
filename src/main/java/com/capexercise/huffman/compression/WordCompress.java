package com.capexercise.huffman.compression;

import com.capexercise.general.IFileReader;
import com.capexercise.general.IMap;
import com.capexercise.general.MapImplementationForWord;
import com.capexercise.general.TreeNode;

public class WordCompress implements Compress{
    @Override
    public IMap calculateFreq(IFileReader fileReader) {
        IMap imap=new MapImplementationForWord();

        String ans=fileReader.readFile();

        String sub="";

        for(int i=0;i<ans.length();i++)
        {
            char currentCharacter = ans.charAt(i);

            while(Character.isAlphabetic(currentCharacter) || Character.isDigit(currentCharacter)){
                sub+=currentCharacter;
                if(i<ans.length()-1)
                    currentCharacter =ans.charAt(++i);
            }

            imap.putFrequency(sub,imap.getFrequency(sub));
            imap.putFrequency(""+currentCharacter,imap.getFrequency(""+currentCharacter));
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
        for(int i=0;i<ans.length();i++)
        {
            char currentCharacter = ans.charAt(i);

            while(Character.isAlphabetic(currentCharacter) || Character.isDigit(currentCharacter)){
                sub+=currentCharacter;
                if(i<ans.length()-1)
                    currentCharacter =ans.charAt(++i);
            }

            if(huffmanMap.containsHuffKey(sub)) {

                finalAns.append(huffmanMap.getHUffmanCode(sub));
            }
            if(huffmanMap.containsHuffKey(""+currentCharacter))
                finalAns.append(huffmanMap.getHUffmanCode(""+currentCharacter));
            sub = "";
        }
        return finalAns;
    }

}
