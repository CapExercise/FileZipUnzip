package com.capexercise.huffman.variations.topword.compressor;

import com.capexercise.general.FileContents;
import com.capexercise.general.IFileContents;
import com.capexercise.general.Path;
import com.capexercise.general.helpers.input.IDataHandle;
import com.capexercise.general.helpers.maps.IMap;
import com.capexercise.general.helpers.maps.WordMaps;
import com.capexercise.general.helpers.nodes.TreeNode;
import com.capexercise.huffman.compression.ICompress;
import com.capexercise.huffman.general.GeneralMethods;
import com.capexercise.huffman.general.IGeneral;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TopWordCompress implements ICompress {
    IGeneral method = new GeneralMethods();
    @Override
    public IMap calculateFreq(IDataHandle dataObj) {
//        long startTIme = System.currentTimeMillis();
//        IMap imap = new WordMaps();
//
//        String[] strData = dataObj.readContentAsArray();
//
//        long end = System.currentTimeMillis();
////        System.out.println("Time to read contents: "+(end-startTIme));
//
////        startTIme = System.currentTimeMillis();
//        for (String str : strData) {
//            imap.putFrequency(str, imap.getFrequency(str));
//        }

//        end = System.currentTimeMillis();
//        System.out.println("Time to generate initial map : "+(end-startTIme));

        long startTime;

        startTime = System.currentTimeMillis();
//
//        IMap imap = new WordMaps();
//
//        String[] strData = dataObj.readContentAsArray();
//        System.out.println("Time to read file:"+(System.currentTimeMillis()-startTime));
//
//        startTime = System.currentTimeMillis();
//        for (String str : strData) {
//            imap.putFrequency(str, imap.getFrequency(str));
//        }
//
//
//        System.out.println("Time to create inital map:"+(System.currentTimeMillis()-startTime));
        IMap imap = new WordMaps();

//        try{
//            FileReader fin = new FileReader(Path.inputFilePath);
//            int val = fin.read();
//            String sub = "";
//            while (val != -1) {
//                char character = (char) val;
//                while (Character.isAlphabetic(character) || Character.isDigit(character)) {
//                    sub += character;
//                    val = fin.read();
//                    character = (char) val;
//                }
//
//                if (sub.length() != 0)
////                stringList.add(sub);
//                    imap.putFrequency(sub, imap.getFrequency(sub));
//                if (val != -1)
////                stringList.add("" + character);
//                    imap.putFrequency(String.valueOf(character), imap.getFrequency(String.valueOf(character)));
//
//
//                sub = "";
//                val = fin.read();
//            }
//            //System.out.println(ans.toString());
//            fin.close();
//        } catch (
//                IOException e) {
//            throw new RuntimeException(e);
//        }
        dataObj.formMap();
        imap.setFreqMap(dataObj.returnMap());
        System.out.println("Time to create inital map:"+(System.currentTimeMillis()-startTime));
        System.out.println("imap frequency size :"+imap.freqSize());
//        return imap;


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

        float size= (float) (38/100.00);
        for(int i=0;i<(keys.size()*size);i++)
            topWordSet.add(keys.get(i));

//        end = System.currentTimeMillis();
//        System.out.println("Time to generate list of top words: "+(end-startTIme));

//        startTIme = System.currentTimeMillis();

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

//        end = System.currentTimeMillis();
//        System.out.println("Time to generate final map : "+(end-startTIme));
        System.out.println("imap final map size:"+imap.freqSize());
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
        System.out.println("str data size"+strData.length);
        for(String str:strData){
            if(huffmanMap.containsHuffKey(str))
                finalAns.append(huffmanMap.getHuffmanCode(str));
            else{
                for(char character:str.toCharArray())
                    finalAns.append(huffmanMap.getHuffmanCode(String.valueOf(character)));
            }
        }
        System.out.println("huffman codes size:"+finalAns.length());
        return finalAns;
    }



    public IFileContents compress(IMap iMap, IDataHandle dataObj) {

//        String fileContents = dataObj.readContent().toString();
        String[] fileContents = dataObj.readContentAsArray();

        System.out.println("read file contents");
        System.out.println(fileContents.length);
        int size = method.getCodeSize(iMap);

        if(size%8 != 0)
            size = (size/8) +1;
        else
            size /= 8;

        byte[] byteArray = new byte[size];
        String curCode = "";
        int idx = 0;
        int outerIndex = 0;

//        System.out.println("forming byte array");
        for (String str:fileContents) {

//            System.out.println(outerIndex++);

            if(iMap.containsHuffKey(str))
                curCode += iMap.getHuffmanCode(str);
            else{
                for(char character:str.toCharArray())
                    curCode += iMap.getHuffmanCode(String.valueOf(character));
            }

                while (curCode.length() > 8) {
                    byte curByte = (byte) Integer.parseInt(curCode.substring(0, 8), 2);
                    curCode = curCode.substring(8);
//                    System.out.println(curByte);
                    byteArray[idx++] = curByte;

            }

        }

//        System.out.println("done");

        int extraBits = 0;

        if (!curCode.equals("")) {
            extraBits = 8 - curCode.length();
            for (int i = 0; i < extraBits; i++)
                curCode += '0';
            byteArray[idx] = (byte) Integer.parseInt(curCode.substring(0, 8), 2);
        }

        System.out.println(extraBits);
        System.out.println(byteArray.length);

//        String serializedTree = method.serialize(root);

        IFileContents compressedData = new FileContents();

//        compressedData.setHuffTree(serializedTree);
//        compressedData.setExtraBits(extraBits);
//        compressedData.setByteArray(byteArray);

        compressedData.setFrequencyMap(iMap.returnFreqMap());
        compressedData.setExtraBits(extraBits);
        compressedData.setByteArray(byteArray);

        return compressedData;

    }



}
