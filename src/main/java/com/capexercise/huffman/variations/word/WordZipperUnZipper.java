package com.capexercise.huffman.variations.word;

import com.capexercise.filezipunzip.FileZipper;
import com.capexercise.general.FrequencyComparator;
import com.capexercise.general.helpers.filedata.IFileContents;
import com.capexercise.general.Path;
import com.capexercise.general.helpers.input.FileHandler;
import com.capexercise.general.helpers.input.IDataHandle;
import com.capexercise.general.helpers.maps.IMap;
import com.capexercise.general.helpers.maps.WordMaps;
import com.capexercise.general.helpers.nodes.StringTreeNode;
import com.capexercise.general.helpers.nodes.TreeNode;
import com.capexercise.huffman.compression.ICompress;
import com.capexercise.huffman.decompression.IDecompress;
import com.capexercise.huffman.general.auxiliary.GeneralMethods;
import com.capexercise.huffman.general.auxiliary.IGeneral;
import com.capexercise.huffman.general.io.InputOutput;
import com.capexercise.huffman.general.io.NormalImplemenation;
import com.capexercise.huffman.variations.word.compressor.WordCompress;
import com.capexercise.huffman.variations.word.decompressor.WordDecompress;

import java.io.*;
import java.util.Map;
import java.util.PriorityQueue;

public class WordZipperUnZipper implements FileZipper {

    IGeneral method;
    WordCompress obj;

    InputOutput io;

    public WordZipperUnZipper(){
        method = new GeneralMethods();
        io=new NormalImplemenation();
    }


    @Override
    public void compress() {

        ICompress compressor = new WordCompress();

        obj = new WordCompress();

        IDataHandle dataObj = new FileHandler(Path.inputFilePath);

        IMap compressionMaps = compressor.calculateFreq(dataObj);

        TreeNode root = this.constructTree(compressionMaps);

        compressor.iterateTreeAndCalculateHuffManCode(root, "", compressionMaps);

        IFileContents fileContents = obj.compress(compressionMaps,dataObj);

        io.addCompressedContents(fileContents);

        System.out.println("Average bit for top word is "+((float)method.getCodeSize(compressionMaps)/(new File(Path.inputFilePath).length())));

        System.out.println("Compression done Successfully");
    }

    @Override
    public void decompress() {
        IDecompress decompressor = new WordDecompress();

        IFileContents fileContents = io.extractContents(new File(Path.compressedFilePath));

        Map<Object, Integer> freq = fileContents.getFrequencyMap();
        int noOfZeros = fileContents.getExtraBits();
        byte[] byteArray = fileContents.getByteArray();

        IMap decompressionMap = new WordMaps();

        decompressionMap.setFreqMap(freq);

        TreeNode root = this.constructTree(decompressionMap);

        decompressor.decompress(byteArray,noOfZeros,root);

        System.out.println("De-Compression done Successfully");


        method.displayStats(Path.inputFilePath, Path.compressedFilePath, Path.decompressedFilePath);
    }

    @Override
    public TreeNode constructTree(IMap imap) {
        PriorityQueue<TreeNode> pq = new PriorityQueue<>(imap.freqSize(), new FrequencyComparator());
        Map<Object, Integer> freq = (Map<Object, Integer>) imap.returnFreqMap();

        for (Map.Entry<Object, Integer> entry : freq.entrySet()) {
            TreeNode node = new StringTreeNode((String) entry.getKey(), entry.getValue());
            pq.add(node);
        }
        TreeNode root = null;
        if (pq.size() == 1) {
            TreeNode leftSideNode = pq.peek();
            pq.poll();
            TreeNode newNode = new StringTreeNode("-", leftSideNode.getFrequency());
            newNode.setLeft(leftSideNode);
            newNode.setRight(null);
            root = newNode;
            return root;
        }
        while (pq.size() > 1) {
            TreeNode leftSideNode = pq.poll();

            TreeNode rightSideNode = pq.poll();
            TreeNode newNode = new StringTreeNode("-", leftSideNode.getFrequency() + rightSideNode.getFrequency());
            newNode.setLeft(leftSideNode);
            newNode.setRight(rightSideNode);
            root = newNode;
            pq.add(newNode);
        }
        return root;
    }

}
