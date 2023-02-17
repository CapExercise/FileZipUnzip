package com.capexercise.huffman.character;

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
import com.capexercise.huffman.character.compressor.CharCompress;
import com.capexercise.huffman.character.decompressor.CharDecompress;
import com.capexercise.huffman.compression.ICompress;
import com.capexercise.huffman.decompression.IDecompress;
import com.capexercise.huffman.general.GeneralMethods;
import com.capexercise.huffman.general.IGeneral;

import java.io.File;
import java.util.Map;
import java.util.PriorityQueue;

public class CharZipperUnZipper implements FileZipper {

    IGeneral method;

    public CharZipperUnZipper() {
        method = new GeneralMethods();
    }

    @Override
    public void compress() {
        ICompress compressor = new CharCompress();

        IDataHandle dataObj = new FileHandler(Path.inputFilePath);

        IMap compressionMaps = compressor.calculateFreq(dataObj);

        TreeNode root = this.constructTree(compressionMaps);

        compressor.iterateTreeAndCalculateHuffManCode(root, "", compressionMaps);

        StringBuilder coded = compressor.getCodes(compressionMaps, dataObj);

        int noOfZerosAppended = compressor.noofZerosToBeAppended(coded);

        if (noOfZerosAppended != 0)
            coded = compressor.appendRemainingZeros(coded);

        byte[] byteArray = compressor.compress(coded);

        compressionMaps.clearHuffMap();

        IFileContents fileContents = new FileContents((Map<Object, Integer>) compressionMaps.returnMap(), noOfZerosAppended, byteArray);
        method.addCompressedContents(fileContents);

        System.out.println("Compression done Successfully");
    }

    @Override
    public void decompress() {
        IDecompress decompressor = new CharDecompress();

        IFileContents fileContents = method.extractContents(new File(Path.compressedFilePath));

        Map<Object, Integer> freq = fileContents.getFrequencyMap();

        int noOfZeros = fileContents.getExtraBits();

        byte[] byteArray = fileContents.getByteArray();

        IMap decompressionMap = new WordMaps(freq, null);

        TreeNode root = this.constructTree(decompressionMap);

        StringBuilder decoded = decompressor.getDecodedString(byteArray);

        decompressor.writeIntoDecompressedFile(root, decoded, noOfZeros);


        System.out.println("De-Compression done Successfully");


        method.displayStats(Path.inputFilePath, Path.compressedFilePath, Path.decompressedFilePath);

    }

    @Override
    public TreeNode constructTree(IMap frequencyMap) {
        PriorityQueue<TreeNode> pq = new PriorityQueue<>(frequencyMap.freqSize(), new FrequencyComparator());
        Map<Object, Integer> freq = (Map<Object, Integer>) frequencyMap.returnMap();

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
