package com.capexercise.huffman.variations.modtopword;

import com.capexercise.filezipunzip.FileZipper;
import com.capexercise.general.FrequencyComparator;
import com.capexercise.general.helpers.checksum.IKeyGenerator;
import com.capexercise.general.helpers.checksum.MD5KeyGenerator;
import com.capexercise.general.helpers.filedata.IFileContents;
import com.capexercise.general.Path;
import com.capexercise.general.helpers.input.FileHandler;
import com.capexercise.general.helpers.input.IDataHandle;
import com.capexercise.general.helpers.maps.IMap;
import com.capexercise.general.helpers.maps.WordMaps;
import com.capexercise.general.helpers.nodes.StringTreeNode;
import com.capexercise.general.helpers.nodes.TreeNode;
import com.capexercise.huffman.general.auxiliary.GeneralMethods;
import com.capexercise.huffman.general.auxiliary.IGeneral;
import com.capexercise.huffman.general.database.IDataBase;
import com.capexercise.huffman.general.io.DBImplementation;
import com.capexercise.huffman.general.io.InputOutput;
import com.capexercise.huffman.variations.modtopword.compressor.CompressionInfo;
import com.capexercise.huffman.variations.modtopword.compressor.CompressionThread;
import com.capexercise.huffman.variations.modtopword.compressor.ModTopWordCompress;
import com.capexercise.huffman.variations.modtopword.decompressor.ModTopWordDecompress;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ModTopWordZipperUnzipper implements FileZipper {
    IGeneral method;
    IDataHandle dataObj;
    InputOutput io;
    IKeyGenerator keyGen;
    IDataBase db;

    @Override
    public void compress(){
        keyGen = new MD5KeyGenerator();
        db = new SQLImplemenation();
        String key = "";
        try {
            key = keyGen.generateKey(Path.inputFilePath);
            dataObj = new FileHandler(Path.inputFilePath);
            method = new GeneralMethods();
            io = new DBImplementation();

            ModTopWordCompress compressor = new ModTopWordCompress();

            long startTime = System.currentTimeMillis();

            IMap compressionMaps = new WordMaps();

            db.setUpConnection();

            boolean flag = db.checkIfKeyExists(key);

            if (flag == true) {
                Map<Object, Integer> freqMap = db.retriveMap(key);
                db.closeConnection();
                compressionMaps.setFreqMap(freqMap);
                TreeNode root = this.constructTree(compressionMaps);
                compressor.iterateTreeAndCalculateHuffManCode(root, "", compressionMaps);
            } else {
                compressionMaps = compressor.calculateFreq(dataObj);
                setOptimalMap(compressionMaps);
                db.addMapIntoTable(key, compressionMaps.returnFreqMap());
                db.closeConnection();
            }


            IFileContents fileContents = compressor.compress(compressionMaps, dataObj);
            fileContents.setMD5Key(key);

            System.out.println("actual time for compression :" + (System.currentTimeMillis() - startTime));

            io.addCompressedContents(fileContents);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        System.out.println("Compression done Successfully");
    }

    @Override
    public void decompress() throws SQLException, IOException, ClassNotFoundException {
        db = new SQLImplemenation();
        ModTopWordDecompress decompressor = new ModTopWordDecompress();

        IFileContents fileContents = io.extractContents(new File(Path.compressedFilePath));

        String key = fileContents.getMD5key();
        int noOfZeros = fileContents.getExtraBits();
        byte[] byteArray = fileContents.getByteArray();

        IMap decompressionMap = new WordMaps();

        db.setUpConnection();
        Map<Object, Integer> freq = db.retriveMap(key);
        db.closeConnection();

        decompressionMap.setFreqMap(freq);

        TreeNode root = this.constructTree(decompressionMap);

        decompressor.decompress(byteArray, noOfZeros, root);

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


    public List<Object> getSortedList(IMap imap) {
        Map<Object, Integer> freqMap = imap.returnFreqMap();

        List<Object> keys = new ArrayList<>(freqMap.keySet());

        Collections.sort(keys, (a, b) -> {
            String str1 = (String) a;
            String str2 = (String) b;
            if (imap.getFrequency(a) == imap.getFrequency(b))
                return str1.compareTo(str2);
            return imap.getFrequency(b) - imap.getFrequency(a);
        });

        return keys;
    }

    public void setOptimalMap(IMap compressionMaps) {
        long startTime = System.currentTimeMillis();

        int cur_min = Integer.MAX_VALUE, prec = 0;


        List<Object> keys = this.getSortedList(compressionMaps);

        ExecutorService service = Executors.newFixedThreadPool(7);
        Map<Integer, CompressionInfo> compressionHash = new HashMap<>();

        for (int i = 1; i < 25; i += 1) {
            Future<CompressionInfo> futureTask1 = service.submit(new CompressionThread(compressionMaps, i, keys));
            Future<CompressionInfo> futureTask2 = service.submit(new CompressionThread(compressionMaps, i + 25, keys));
            Future<CompressionInfo> futureTask3 = service.submit(new CompressionThread(compressionMaps, i + 50, keys));
            Future<CompressionInfo> futureTask4 = service.submit(new CompressionThread(compressionMaps, i + 75, keys));


            int sum1, sum2, sum3, sum4;
            CompressionInfo obj1, obj2, obj3, obj4;
            try {
                obj1 = futureTask1.get();
                obj2 = futureTask2.get();
                obj3 = futureTask3.get();
                obj4 = futureTask4.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }

            if ((System.currentTimeMillis() - startTime) / 1000 > 50)
                break;


            sum1 = obj1.getSize();
            sum2 = obj2.getSize();
            sum3 = obj3.getSize();
            sum4 = obj4.getSize();

            compressionHash.put(i, obj1);
            compressionHash.put(i + 25, obj2);
            compressionHash.put(i + 50, obj3);
            compressionHash.put(i + 75, obj4);

            if (Math.min(Math.min(sum1, sum2), Math.min(sum3, sum4)) < cur_min) {

                cur_min = (int) Math.min(Math.min(sum1, sum2), Math.min(sum3, sum4));
                if (cur_min == sum1)
                    prec = i;
                else if (cur_min == sum2)
                    prec = i + 25;
                else if (cur_min == sum3)
                    prec = i + 50;
                else
                    prec = i + 75;
            } else
                break;
        }
        service.shutdown();
        System.out.println("actual time for finding percentage :" + (System.currentTimeMillis() - startTime));
        System.out.println("percentage used for compression is " + prec);


        CompressionInfo myObject = compressionHash.get(prec);

        compressionHash.clear();

        compressionMaps.setFreqMap(myObject.getFreqMap());

        compressionMaps.setHuffMap(myObject.getHuffMap());
    }


}