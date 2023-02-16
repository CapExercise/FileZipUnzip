package com.capexercise.huffman;

import com.capexercise.filezipunzip.FileZipper;
import com.capexercise.general.*;
import com.capexercise.huffman.compression.Compress;
import com.capexercise.huffman.compression.WordCompress;
import com.capexercise.huffman.decompression.CharDecompress;
import com.capexercise.huffman.decompression.Decompress;
import com.capexercise.huffman.decompression.WordDecompress;

import java.io.*;
import java.util.Map;
import java.util.PriorityQueue;

public class WordZipperUnZipper implements FileZipper {
    @Override
    public void compress()
    {
        
      Compress c=new WordCompress();

      GeneralInterface generalInterface=new GeneralClassImplementation();

        IFileReader fop=new ImplementationForFileOpearations(Path.inputFilePath);
        try
        {
            IMap compressionMaps = c.calculateFreq(fop);
            TreeNode root=this.constructTree(compressionMaps);
            c.iterateTreeAndCalculateHuffManCode(root, "",compressionMaps);
            StringBuilder coded=c.getCodes(compressionMaps,fop);
            int noOfZerosAppended =generalInterface.noofZerosToBeAppended(coded);
            if(noOfZerosAppended !=0)
            {
                coded = generalInterface.appendRemainingZeros(coded);
            }
            byte[] byteArray=generalInterface.compress(coded);

            compressionMaps.clearHuffMap();
            ObjectOutputStream outStream=new ObjectOutputStream(new FileOutputStream(Path.compressedFilePath));
            outStream.writeObject(compressionMaps.returnMap());
            outStream.writeInt(noOfZerosAppended);
            outStream.writeObject(byteArray);
            outStream.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        System.out.println("Compression done Successfully");
    }

    @Override
    public void decompress() {
        Decompress d = new WordDecompress();

        GeneralInterface generalInterface=new GeneralClassImplementation();
        try
        {
            //
            ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(Path.compressedFilePath));
            Map<String,Integer> freq=(Map<String, Integer>) inStream.readObject();
            IMap decompressionMap=new MapImplementationForWord(freq,null);
            TreeNode  root=this.constructTree(decompressionMap);
            int noOfZeros=inStream.readInt();
            byte[] byteArray= (byte[])inStream.readObject();
            StringBuilder decoded=d.getDecodedString(byteArray);
            d.writeIntoDecompressedFile(root,decoded,noOfZeros);

        }
        catch (IOException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }

        System.out.println("De-Compression done Successfully");


        generalInterface.displayStats(Path.inputFilePath,Path.compressedFilePath,Path.decompressedFilePath);
    }

    @Override
    public TreeNode constructTree(IMap imap) {
        PriorityQueue<TreeNode> pq = new PriorityQueue<>(imap.freqSize(), new FrequencyComparator());
        Map<String,Integer> freq= (Map<String, Integer>) imap.returnMap();

        for (Map.Entry<String, Integer> entry : freq.entrySet())
        {
            TreeNode nd = new StringTreeNode(entry.getKey(),entry.getValue());
            pq.add(nd);
        }
        TreeNode root = null;
        if(pq.size()==1)
        {
            TreeNode leftSideNode=pq.peek();
            pq.poll();
            TreeNode newNode = new StringTreeNode("-", leftSideNode.getFrequency());
            newNode.setLeft(leftSideNode);
            newNode.setRight(null);
            root=newNode;
            return root;
        }
        while (pq.size() > 1) {
            TreeNode leftSideNode= pq.poll();

            TreeNode rightSideNode = pq.poll();
            TreeNode newNode = new StringTreeNode("-",leftSideNode.getFrequency() + rightSideNode.getFrequency());
            newNode.setLeft(leftSideNode);
            newNode.setRight(rightSideNode);
            root = newNode;
            pq.add(newNode);
        }
        return root;
    }

}