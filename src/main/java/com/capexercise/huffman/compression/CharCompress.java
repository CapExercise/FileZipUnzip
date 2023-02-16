package com.capexercise.huffman.compression;

import com.capexercise.general.*;

import java.util.*;

public class CharCompress implements Compress
{
    @Override
    public IMap calculateFreq(IFileReader fileReader)
    {
       // Map<Character, Integer> frequencyMap = new HashMap<>();
        IMap imap=new MapImplemenationForChar();

        int c =0;

        String ans=fileReader.readFile();

        for(char x:ans.toCharArray())
        {
            int val=imap.getFrequency(x+"");
            imap.putFrequency(x+"",val);
            //frequencyMap.put(x,frequencyMap.getOrDefault(x,0)+1);
        }
        return imap;
    }

    @Override
    public void iterateTreeAndCalculateHuffManCode(TreeNode newNode, String s,IMap huffmanMap)
    {
        if(newNode==null) {
            return;
        }
        if(newNode.getLeft()==null && newNode.getRight()==null)
        {
          //  huffmanMap.putHuffManCode((String) newNode.getVar(),s);
            huffmanMap.putHuffManCode(newNode.getVar()+"",s);
        }
        iterateTreeAndCalculateHuffManCode(newNode.getLeft(),s+"0",huffmanMap);
        iterateTreeAndCalculateHuffManCode(newNode.getRight(),s+"1",huffmanMap);
    }

    @Override
    public StringBuilder getCodes(IMap huffmanMap, IFileReader fobj)
    {
        StringBuilder ans=new StringBuilder();
        String curr=fobj.readFile();
        for(char x:curr.toCharArray())
        {
            ans.append(huffmanMap.getHUffmanCode(x+""));
        }
        return ans;
    }

}
