package com.capexercise.huffman.word.compressor;

import com.capexercise.general.helpers.input.IDataHandle;
import com.capexercise.general.helpers.nodes.TreeNode;
import com.capexercise.general.helpers.maps.IMap;
import com.capexercise.general.helpers.maps.MapImplementationForWord;
import com.capexercise.huffman.compression.Compress;

public class WordCompress implements Compress {
    @Override
    public IMap calculateFreq(IDataHandle dataObj) {
        IMap imap=new MapImplementationForWord();

      String[] strData = dataObj.readContentAsArray();

        for(String str:strData){
            imap.putFrequency(str, imap.getFrequency(str));
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
    public StringBuilder getCodes(IMap huffmanMap, IDataHandle fobj) {
        StringBuilder finalAns=new StringBuilder();
        String ans=fobj.readContent();
        String sub="";
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

}
