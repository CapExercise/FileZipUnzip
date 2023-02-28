package com.capexercise.huffman.variations.character;

import com.capexercise.filezipunzip.FileZipper;
import com.capexercise.general.FileContents;
import com.capexercise.general.FrequencyComparator;
import com.capexercise.general.IFileContents;
import com.capexercise.general.Path;
import com.capexercise.general.helpers.input.FileHandler;
import com.capexercise.general.helpers.input.IDataHandle;
import com.capexercise.general.helpers.maps.IMap;
import com.capexercise.general.helpers.maps.WordMaps;
import com.capexercise.general.helpers.nodes.CharTreeNode;
import com.capexercise.general.helpers.nodes.TreeNode;
import com.capexercise.huffman.variations.character.compressor.CharCompress;
import com.capexercise.huffman.variations.character.decompressor.CharDecompress;
import com.capexercise.huffman.compression.ICompress;
import com.capexercise.huffman.decompression.IDecompress;
import com.capexercise.huffman.general.GeneralMethods;
import com.capexercise.huffman.general.IGeneral;

import java.io.*;
import java.util.Map;
import java.util.PriorityQueue;

public class CharZipperUnZipper implements FileZipper {
    CharCompress obj;
    IGeneral method;

    public CharZipperUnZipper() {
        method = new GeneralMethods();
    }

    @Override
    public void compress() {
        obj = new CharCompress();
        ICompress compressor = new CharCompress();

        IDataHandle dataObj = new FileHandler(Path.inputFilePath);

        IMap compressionMaps = compressor.calculateFreq(dataObj);

        TreeNode root = this.constructTree(compressionMaps);

        compressor.iterateTreeAndCalculateHuffManCode(root, "", compressionMaps);

        IFileContents fileContents = compressor.compress(compressionMaps,dataObj);

        method.addCompressedContents(fileContents);

        float size = (float) method.getCodeSize(compressionMaps);
        System.out.println("Average bit for Char is "+(size/(new File(Path.inputFilePath).length())));
        System.out.println("Compression done Successfully");
    }

    @Override
    public void decompress() {
        IDecompress decompressor = new CharDecompress();

        IFileContents fileContents = method.extractContents(new File(Path.compressedFilePath));

        Map<Object, Integer> freqMap = fileContents.getFrequencyMap();

        int noOfZeros = fileContents.getExtraBits();

        byte[] byteArray = fileContents.getByteArray();

        IMap decompressionMap = new WordMaps();

        decompressionMap.setFreqMap(freqMap);

        TreeNode root = this.constructTree(decompressionMap);

        decompressor.decompress(byteArray,noOfZeros, root);

        System.out.println("De-Compression done Successfully");


        method.displayStats(Path.inputFilePath, Path.compressedFilePath, Path.decompressedFilePath);

    }

    @Override
    public TreeNode constructTree(IMap frequencyMap) {
        PriorityQueue<TreeNode> pq = new PriorityQueue<>(frequencyMap.freqSize(), new FrequencyComparator());
        Map<Object, Integer> freq = frequencyMap.returnFreqMap();

        for (Map.Entry<Object, Integer> entry : freq.entrySet()) {
            TreeNode node = new CharTreeNode((Character) entry.getKey(), entry.getValue());
            pq.add(node);
        }
        TreeNode root = null;
        if (pq.size() == 1) {
            TreeNode leftSideNode = pq.peek();
            pq.poll();
            TreeNode newNode = new CharTreeNode('-', leftSideNode.getFrequency());
            newNode.setLeft(leftSideNode);
            newNode.setRight(null);
            root = newNode;
            return root;
        }
        while (pq.size() > 1) {
            TreeNode leftSideNode = pq.poll();
            TreeNode rightSideNode = pq.poll();
            TreeNode newNode = new CharTreeNode('-', leftSideNode.getFrequency() + rightSideNode.getFrequency());
            newNode.setLeft(leftSideNode);
            newNode.setRight(rightSideNode);
            root = newNode;
            pq.add(newNode);
        }
        return root;
    }
}
