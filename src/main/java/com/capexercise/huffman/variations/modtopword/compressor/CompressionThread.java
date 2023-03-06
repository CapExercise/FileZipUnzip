package com.capexercise.huffman.variations.modtopword.compressor;

import com.capexercise.general.FrequencyComparator;
import com.capexercise.general.helpers.maps.IMap;
import com.capexercise.general.helpers.maps.WordMaps;
import com.capexercise.general.helpers.nodes.StringTreeNode;
import com.capexercise.general.helpers.nodes.TreeNode;
import com.capexercise.huffman.compression.ICompress;
import com.capexercise.huffman.general.auxiliary.GeneralMethods;
import com.capexercise.huffman.general.auxiliary.IGeneral;

import java.util.*;
import java.util.concurrent.Callable;

public class CompressionThread implements Callable<CompressionInfo> {
    IGeneral method;
    ICompress compressor;

    IMap compressionMap;
    List<Object> keys;

    int percentage;
    CompressionThread()
    {

    }

    public CompressionThread(IMap map, int percentage, List<Object> keys) {
        this.compressionMap = map;
        this.percentage = percentage;
        this.keys = keys;
        method = new GeneralMethods();
        compressor = new ModTopWordCompress();
    }

    @Override
    public CompressionInfo call() throws Exception {

        IMap tempMap = this.getOptimalMap(this.compressionMap, this.percentage, this.keys);
        TreeNode root = this.constructTree(tempMap);

        compressor.iterateTreeAndCalculateHuffManCode(root, "", tempMap);

        int sum = method.getFreqSize(tempMap) + (method.getCodeSize(tempMap) / 8);

        CompressionInfo compressionInfo = new CompressionInfo(this.percentage, sum, tempMap.returnFreqMap(), tempMap.returnHuffMap());

        return compressionInfo;
    }


    IMap getOptimalMap(IMap imap, int perc, List<Object> keys) {

        IMap finalMap = new WordMaps();

        float size = (float) (perc / 100.00);
        Set<Object> topWordSet = new HashSet<>(keys.subList(0, (int) (keys.size() * size)));


        Map<Object, Integer> newMap = new HashMap<>();
        for (Object obj : keys) {
            String str = (String) obj;
            if (str.length() != 0) {
                if (!topWordSet.contains(str)) {
                    int val = imap.getFrequency(obj);
                    for (int idx = 0; idx < str.length(); idx++) {
                        newMap.put(String.valueOf(str.charAt(idx)), newMap.getOrDefault(String.valueOf(str.charAt(idx)), 0) + val);
                    }
                } else
                    newMap.put(str, imap.getFrequency(obj));

            }

        }

        finalMap.setFreqMap(newMap);

        return finalMap;
    }

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