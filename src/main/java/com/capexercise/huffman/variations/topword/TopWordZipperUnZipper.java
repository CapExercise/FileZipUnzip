package com.capexercise.huffman.variations.topword;

import com.capexercise.filezipunzip.FileZipper;
import com.capexercise.general.FileContents;
import com.capexercise.general.FrequencyComparator;
import com.capexercise.general.IFileContents;
import com.capexercise.general.Path;
import com.capexercise.general.helpers.input.FileHandler;
import com.capexercise.general.helpers.input.IDataHandle;
import com.capexercise.general.helpers.maps.IMap;
import com.capexercise.general.helpers.maps.WordMaps;
import com.capexercise.general.helpers.nodes.StringTreeNode;
import com.capexercise.general.helpers.nodes.TreeNode;
import com.capexercise.huffman.compression.ICompress;
import com.capexercise.huffman.decompression.IDecompress;
import com.capexercise.huffman.general.GeneralMethods;
import com.capexercise.huffman.general.IGeneral;
import com.capexercise.huffman.variations.topword.compressor.TopWordCompress;
import com.capexercise.huffman.variations.topword.decompressor.TopWordDecompress;
import sun.rmi.runtime.Log;

import java.io.*;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.logging.Logger;

public class TopWordZipperUnZipper implements FileZipper {

    IGeneral method;

    public TopWordZipperUnZipper(){
        method = new GeneralMethods();
    }

    Logger logger = Logger.getLogger(TopWordZipperUnZipper.class.getName());
    long startTime;
    long freq,tree,huff,code,comp,write;
    @Override
    public void compress() {
        ICompress compressor = new TopWordCompress();

        IDataHandle dataObj = new FileHandler(Path.inputFilePath);

        startTime = System.currentTimeMillis();
        IMap compressionMaps = compressor.calculateFreq(dataObj);
//        freq = System.currentTimeMillis() - startTime;

//        startTime = System.currentTimeMillis();
        TreeNode root = this.constructTree(compressionMaps);
//        tree = System.currentTimeMillis() - startTime;

//        startTime = System.currentTimeMillis();
        compressor.iterateTreeAndCalculateHuffManCode(root, "", compressionMaps);
//        huff = System.currentTimeMillis() - startTime;

//        startTime = System.currentTimeMillis();
        StringBuilder coded = compressor.getCodes(compressionMaps, dataObj);
//        code = System.currentTimeMillis() - startTime;

        int noOfZerosAppended = compressor.noofZerosToBeAppended(coded);

        if (noOfZerosAppended != 0)
            coded = compressor.appendRemainingZeros(coded);

//        startTime = System.currentTimeMillis();
        byte[] byteArray = compressor.compress(coded);
//        comp = System.currentTimeMillis() - startTime;

        compressionMaps.clearHuffMap();

//        startTime = System.currentTimeMillis();
        IFileContents fileContents = new FileContents(compressionMaps.returnFreqMap(), noOfZerosAppended, byteArray);
        method.addCompressedContents(fileContents);
        File f=new File("src/main/java/com/capexercise/Files/mapSize.txt");
        try
        {
            ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(f));
            out.writeObject(compressionMaps.returnFreqMap());
            out.close();
            System.out.println("top Size of map is "+f.length());
            System.out.println("Average bit for top word is "+((float)coded.length()/(new File("src/main/java/com/capexercise/Files/input.txt").length())));
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
//        write = System.currentTimeMillis();
//        logger.info("Time to build frequency map:"+freq+"\nTime to build tree:"+tree+"\ntime to build huffman map:"+huff+"\ntime to get encoded string:"+code+"\nTime to get compressed data:"+comp+"\nTime to write:"+write);

        System.out.println("Compression done Successfully");
    }

    @Override
    public void decompress() {
        IDecompress decompressor = new TopWordDecompress();
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
    public TreeNode constructTree(IMap imap) {
        PriorityQueue<TreeNode> pq = new PriorityQueue<>(imap.freqSize(), new FrequencyComparator());
        Map<Object, Integer> freq = imap.returnFreqMap();

        for (Map.Entry<Object, Integer> entry : freq.entrySet()) {
            TreeNode node = new StringTreeNode(entry.getKey(), entry.getValue());
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
